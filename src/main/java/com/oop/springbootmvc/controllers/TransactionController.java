package com.oop.springbootmvc.controllers;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
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
    public ResponseEntity<Object> revenueGeneratedByEventID(@PathVariable String status, @PathVariable int eventId) {
        try {
            int revenueGenerated = transactionService.revenueGeneratedByEventID(status, eventId);
            if (revenueGenerated != 0){
                return ResponseEntity.ok(revenueGenerated);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(403).body("");
        }
    }
    
}
