package com.oop.springbootmvc.controllers;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.oop.springbootmvc.viewmodel.PurchaseViewModel;

@RestController
public class TransactionController {
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final TransactionRepository transactionRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;


    public TransactionController(TicketRepository ticketRepository, EventRepository eventRepository, TransactionRepository transactionRepository, SeatRepository seatRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.transactionRepository = transactionRepository;
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;

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
            t.setPurchasedDateTime(Timestamp.from(Instant.now()));

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
            var transactions = u.getTransactions();
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

                    for(Ticket tic: t.getTickets()){
                        tic.setStatus("Cancelled");
                        ticketRepository.save(tic);
                    }
                    
                    u.setBalance(u.getBalance()+(t.getCost()-fee));
                    userRepository.save(u);
                    return ResponseEntity.ok().body("");

                }
            }
            return ResponseEntity.notFound().build();
        }catch(Exception e){
            return ResponseEntity.status(403).body("");

        }
    }
}
