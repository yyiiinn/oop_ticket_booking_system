package com.oop.springbootmvc.controllers;

import java.security.Principal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oop.springbootmvc.model.CustomUserDetails;
import com.oop.springbootmvc.model.Event;
import com.oop.springbootmvc.model.Seat;
import com.oop.springbootmvc.model.Ticket;
import com.oop.springbootmvc.model.Transaction;
import com.oop.springbootmvc.model.User;
import com.oop.springbootmvc.repository.EventRepository;
import com.oop.springbootmvc.repository.SeatRepository;
import com.oop.springbootmvc.repository.TicketRepository;
import com.oop.springbootmvc.repository.TransactionRepository;
import com.oop.springbootmvc.repository.UserRepository;
import com.oop.springbootmvc.viewmodel.CustomerBookingDetailsViewModel;
import com.oop.springbootmvc.viewmodel.CustomerCancelTransactionViewModel;
import com.oop.springbootmvc.viewmodel.CustomerTicketViewModel;
import com.oop.springbootmvc.viewmodel.CustomerTransactionViewModel;
import com.oop.springbootmvc.viewmodel.OfficerPurchaseViewModel;
import com.oop.springbootmvc.viewmodel.PurchaseViewModel;

import com.oop.springbootmvc.service.EmailService;
import com.oop.springbootmvc.service.TransactionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class TransactionController {
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final TransactionRepository transactionRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;


    public TransactionController(TicketRepository ticketRepository, EventRepository eventRepository, TransactionRepository transactionRepository, SeatRepository seatRepository, UserRepository userRepository, EmailService emailService) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.transactionRepository = transactionRepository;
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;

    }
    @GetMapping("/api/Customer/transactions")
    public ResponseEntity<Object> getTransactions(Principal principal) {
        try{
            Authentication authentication = (Authentication) principal;
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            User tempUser = user.getUser();
            User u = userRepository.findById(tempUser.getId()).get();
            ArrayList<CustomerTransactionViewModel> toReturn = new ArrayList<>();
            for(Transaction t: u.getTransactions()){
                toReturn.add(new CustomerTransactionViewModel(t));
            }
            toReturn.sort(Comparator.comparing(CustomerTransactionViewModel::getId).reversed());;

            return ResponseEntity.ok().body(toReturn);
        }catch(Exception e){
            System.out.println(e);
            return ResponseEntity.status(403).body("");

        }
    }
    @GetMapping("/api/Customer/transaction/{transactionId}")
    public ResponseEntity<Object> getTransaction(@PathVariable("transactionId") int transactionId, Principal principal) {
        try{
            Authentication authentication = (Authentication) principal;
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            User tempUser = user.getUser();
            User u = userRepository.findById(tempUser.getId()).get();
            for(Transaction t: u.getTransactions()){
                if (t.getId() == transactionId){
                    ArrayList<CustomerTicketViewModel> toReturn = new ArrayList<>();
                    Event e = t.getSeat().getEvent();
                    for (Ticket tic: t.getTickets()){
                        toReturn.add(new CustomerTicketViewModel(tic, t.getSeat().getType()));
                    }
                    return ResponseEntity.ok().body(new CustomerBookingDetailsViewModel(e.getName(), e.getEventStartDate(), e.getEventStartTime(), e.getEventEndTime(), toReturn));
                }
            }
            return ResponseEntity.notFound().build();
        }catch(Exception e){
            System.out.println(e);
            return ResponseEntity.status(403).body("");

        }
    }
    @PostMapping("/api/Customer/purchase")
    public ResponseEntity<Object> createTransaction(@RequestBody PurchaseViewModel purchaseViewModel, Principal principal) {
        try{
            Authentication authentication = (Authentication) principal;
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            User tempUser = user.getUser();
            User u = userRepository.findById(tempUser.getId()).get();
            Transaction t = new Transaction();
            t.setStatus("Booked");
            Timestamp purchasedDateTime = Timestamp.from(Instant.now());
            t.setPurchasedDateTime(purchasedDateTime);

            Optional<Seat> s = seatRepository.findById(purchaseViewModel.getSitId());
            if(s.isPresent()){
                Seat seat = s.get();
                t.setCost(seat.getCost() * purchaseViewModel.getQuantity());

                // User does not have enough balance
                if (u.getBalance() < t.getCost()){
                    return ResponseEntity.badRequest().build();
                }
                
                // Event does not have enough seats
                Event e = seat.getEvent();
                int booked = 0;
                for (Seat se :e.getSeats()){
                    // Get All Transactions
                    if (se.getId() == seat.getId()){
                        for (Transaction tr: se.getTransactions()){
                            if(tr.getStatus().equals("Paid")){
                                booked += tr.getTickets().size();
                            }
                        };
                        break;
                    }
                }
                if ((seat.getNumberOfSeats()- booked)<purchaseViewModel.getQuantity()){
                    return ResponseEntity.badRequest().build();
                }

                t.setCancellationCost(0);
                t.setUser(u);
                t.setSeat(seat);
                Transaction confirmed = transactionRepository.save(t);
                ArrayList<Ticket> tickets = new ArrayList<>();
                for (int i = 0; i < purchaseViewModel.getQuantity(); i++) {
                    Ticket ticket = new Ticket();
                    ticket.setStatus("Usable");
                    ticket.setGuid(java.util.UUID.randomUUID().toString());
                    ticket.setTransaction(confirmed);
                    tickets.add(ticket);
                } 
                ticketRepository.saveAll(tickets);
                u.setBalance(u.getBalance() - t.getCost());
                userRepository.save(u);
                purchasedDateTime.setNanos(0);
                String toSend = "Hi " + u.getName() + ". \n\nThank you for the purchase! These are your purchase details for event " +  e.getName() + " made on " + purchasedDateTime + ". \n\n";
                int count = 1;
                for (Ticket tic : tickets){
                    toSend += "Ticket " + count + ": \n";
                    toSend += "Seat Type: " + seat.getType() + "\n";
                    toSend += "Guid: " + tic.getGuid() + "\n\n";
                    count++;
                }
                toSend += "Regards, \nTicketing Team";
                this.emailService.sendMessage(u.getUsername(),"Ticket Purchase Details",toSend);
    

                return ResponseEntity.ok().body("");
            }else{
                return ResponseEntity.notFound().build();

            }
        }catch(Exception e){
            return ResponseEntity.status(403).body("");

        }
    }

    @PostMapping("/api/officer/purchase")
    public ResponseEntity<Object> createTransaction(@RequestBody OfficerPurchaseViewModel officerPurchaseViewModel) {
        try{
            Optional<User> optionalUser = userRepository.findByUsername(officerPurchaseViewModel.getUsername());
            if (!optionalUser.isPresent()) {
                return ResponseEntity.badRequest().build();
            }
            User u = optionalUser.get();
            Transaction t = new Transaction();
            t.setStatus("Booked");
            Timestamp purchasedDateTime = Timestamp.from(Instant.now());
            t.setPurchasedDateTime(purchasedDateTime);

            Optional<Seat> s = seatRepository.findById(officerPurchaseViewModel.getSitId());
            if(s.isPresent()){
                Seat seat = s.get();
                t.setCost(seat.getCost() * officerPurchaseViewModel.getQuantity());

                // User does not have enough balance
                if (u.getBalance() < t.getCost()){
                    return ResponseEntity.badRequest().build();
                }
                
                // Event does not have enough seats
                Event e = seat.getEvent();
                int booked = 0;
                for (Seat se :e.getSeats()){
                    // Get All Transactions
                    if (se.getId() == seat.getId()){
                        for (Transaction tr: se.getTransactions()){
                            if(tr.getStatus().equals("Paid")){
                                booked += tr.getTickets().size();
                            }
                        };
                        break;
                    }
                }
                if ((seat.getNumberOfSeats()- booked)<officerPurchaseViewModel.getQuantity()){
                    return ResponseEntity.badRequest().build();
                }

                t.setCancellationCost(0);
                t.setUser(u);
                t.setSeat(seat);
                Transaction confirmed = transactionRepository.save(t);
                ArrayList<Ticket> tickets = new ArrayList<>();
                for (int i = 0; i < officerPurchaseViewModel.getQuantity(); i++) {
                    Ticket ticket = new Ticket();
                    ticket.setStatus("Usable");
                    ticket.setGuid(java.util.UUID.randomUUID().toString());
                    ticket.setTransaction(confirmed);
                    tickets.add(ticket);
                } 
                ticketRepository.saveAll(tickets);
                u.setBalance(u.getBalance() - t.getCost());
                userRepository.save(u);
                purchasedDateTime.setNanos(0);
                String toSend = "Hi " + u.getName() + ". \n\nThank you for the purchase! These are your purchase details for event " +  e.getName() + " made on " + purchasedDateTime + ". \n\n";
                int count = 1;
                for (Ticket tic : tickets){
                    toSend += "Ticket " + count + ": \n";
                    toSend += "Seat Type: " + seat.getType() + "\n";
                    toSend += "Guid: " + tic.getGuid() + "\n\n";
                    count++;
                }
                toSend += "Regards, \nTicketing Team";
                this.emailService.sendMessage(u.getUsername(),"Ticket Purchase Details",toSend);
    

                return ResponseEntity.ok().body("");
            }else{
                return ResponseEntity.notFound().build();

            }
        }catch(Exception e){
            return ResponseEntity.status(403).body("");

        }
    }

    @PostMapping("/api/Customer/cancel")
    public ResponseEntity<Object> cancelTransaction(@RequestBody CustomerCancelTransactionViewModel customerCancelTransactionViewModel, Principal principal) {
        try{
            Authentication authentication = (Authentication) principal;
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            User tempUser = user.getUser();
            User u = userRepository.findById(tempUser.getId()).get();
            Set<Transaction> transactions = u.getTransactions();
            for(Transaction t: transactions){
                if(t.getId() == customerCancelTransactionViewModel.getId()){
                    if (t.getStatus().equals("Cancelled")){
                        return ResponseEntity.notFound().build();
                    }
                    if (t.getStatus().equals("Refunded")){
                        return ResponseEntity.notFound().build();
                    }
                    Event e = t.getSeat().getEvent();
                    float fee = e.getCancellationFee() * t.getTickets().size();
                    t.setCancellationCost(fee);
                    t.setStatus("Cancelled");
                    transactionRepository.save(t);
                    Timestamp purchasedDateTime = Timestamp.from(Instant.now());
                    t.setPurchasedDateTime(purchasedDateTime);
                    purchasedDateTime.setNanos(0);

                    String toSend = "Hi " + u.getName() + ". \n\nYou have cancelled the booking for event " +  e.getName() + "made on " + purchasedDateTime + ". \n\n";

                    int ticketCount = 0;
                    for(Ticket tic: t.getTickets()){
                        tic.setStatus("Cancelled");
                        ticketRepository.save(tic);
                        ticketCount++;
                    }
                    u.setBalance(u.getBalance()+(t.getCost()-fee));
                    userRepository.save(u);
                    toSend += "Total of $" + (t.getCost()-fee) + " have been refunded to your account as there is a cancellation fee of $" + fee + ".\n\nRegards, \nTicketing Team";
                    this.emailService.sendMessage(u.getUsername(),"Ticket Purchase Details",toSend);

                    return ResponseEntity.ok().body("");

                }
            }
            return ResponseEntity.notFound().build();
        }catch(Exception e){
            return ResponseEntity.status(403).body("");

        }
    }

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/api/manager/ViewDashboard/revenueGenerated/{status}/{eventId}")
    public ResponseEntity<Object> revenueGeneratedByEventID(@PathVariable String status, @PathVariable(required = false) Integer eventId) {
        try {
            int revenueGenerated;
            if (eventId != null && eventId != 0) {
                revenueGenerated = transactionService.revenueGeneratedByEventID(status, eventId);
            } else {
                revenueGenerated = transactionService.totalRevenueGenerated(status);
            }

            if (revenueGenerated != 0) {
                return ResponseEntity.ok(revenueGenerated);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Handle exception
            return ResponseEntity.status(403).body("");
        }
    }

    @GetMapping(value = "/api/manager/ViewDashboard/totalTransactions/{eventId}")
    public ResponseEntity<Object> totalTransactionsByEventId(@PathVariable int eventId) {
        try {
            Optional<Event> optionalEvent = eventRepository.findById(eventId);
            int transactions = 0;
            int totalTransactionsBooked = 0;
            int totalTransactionsCancelled = 0;
            int totalTransactionsRefunded = 0;
            Map<String, Object> jsonResponse = new HashMap<>();
            if (optionalEvent.isPresent()){
              Event event = optionalEvent.get();
              Set<Seat> seats = event.getSeats();
              event.setSeats(seats);
              for (Seat s :seats){
                transactions += s.getTransactions().size();
                totalTransactionsBooked += transactionService.countTransactionByStatusAndSeatId("Booked", s.getId());
                totalTransactionsCancelled += transactionService.countTransactionByStatusAndSeatId("Cancelled", s.getId());
                totalTransactionsRefunded += transactionService.countTransactionByStatusAndSeatId("Refunded", s.getId());
              }
            } else {
              transactions = transactionService.countTotalTransactions();
              totalTransactionsBooked = transactionService.countTransactionByStatus("Booked");
              totalTransactionsCancelled = transactionService.countTransactionByStatus("Cancelled");
              totalTransactionsRefunded = transactionService.countTransactionByStatus("Refunded");
            }
            jsonResponse.put("TotalTransactions", transactions);
            jsonResponse.put("totalTransactionsBooked", totalTransactionsBooked);
            jsonResponse.put("totalTransactionsCancelled", totalTransactionsCancelled);
            jsonResponse.put("totalTransactionsRefunded", totalTransactionsRefunded);
            return ResponseEntity.ok().body(jsonResponse);
          } catch (Exception e) {
              // TODO: handle exception
              return ResponseEntity.status(403).body("");
          }
    }

    @GetMapping("/api/manager/ViewDashboard/TransactionCountByMonth/{eventId}")
    public ResponseEntity<Object> transactionCountByMonth(@PathVariable int eventId) {
        try {
            Optional<Event> optionalEvent = eventRepository.findById(eventId);
            Map<String, Map<String, Integer>> monthTransactionCount = new HashMap<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM", Locale.ENGLISH);
            // Initialize months and statuses
            String[] statuses = {"Cancelled", "Refunded", "Booked"}; 
            Calendar cal = Calendar.getInstance();
            for (int i = 0; i < 12; i++) {
                cal.set(Calendar.MONTH, i);
                String monthAbbreviation = dateFormat.format(cal.getTime());
                Map<String, Integer> statusMap = new HashMap<>();
                for (String status : statuses) {
                    statusMap.put(status, 0);
                }
                monthTransactionCount.put(monthAbbreviation, statusMap);
            }

            // Fetch data
            if (optionalEvent.isPresent()) {
                Event event = optionalEvent.get();
                Set<Seat> seats = event.getSeats();
                for (Seat seat : seats) {
                    for (Transaction transaction : seat.getTransactions()) {
                        String monthAbbreviation = dateFormat.format(new Date(transaction.getPurchasedDateTime().getTime()));
                        String status = transaction.getStatus(); 
                        Map<String, Integer> statusMap = monthTransactionCount.get(monthAbbreviation);
                        statusMap.put(status, statusMap.getOrDefault(status, 0) + 1);
                    }
                }
            } else {
                List<Transaction> transactions = (List<Transaction>) transactionRepository.findAll();
                for (Transaction transaction : transactions) {
                    String monthAbbreviation = dateFormat.format(new Date(transaction.getPurchasedDateTime().getTime()));
                    String status = transaction.getStatus();
                    Map<String, Integer> statusMap = monthTransactionCount.get(monthAbbreviation);
                    statusMap.put(status, statusMap.getOrDefault(status, 0) + 1);
                }
            }

            // Format data for response
            Map<String, Map<String, Integer>> sortedMonthTransactionCount = new LinkedHashMap<>();
            for (int i = 0; i < 12; i++) {
                cal.set(Calendar.MONTH, i);
                String monthAbbreviation = dateFormat.format(cal.getTime());
                sortedMonthTransactionCount.put(monthAbbreviation, monthTransactionCount.get(monthAbbreviation));
            }

            return ResponseEntity.ok().body(sortedMonthTransactionCount);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server Error: " + e.getMessage());
        }
    }
    
    
    
}
