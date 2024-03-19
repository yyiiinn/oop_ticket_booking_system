package com.oop.springbootmvc.controllers;

import org.springframework.stereotype.Controller;

import com.oop.springbootmvc.model.Transaction;
import com.oop.springbootmvc.model.User;
import com.oop.springbootmvc.repository.TransactionRepository;
import com.oop.springbootmvc.repository.UserRepository;
import com.oop.springbootmvc.model.Event;
import com.oop.springbootmvc.repository.EventRepository;
import com.oop.springbootmvc.repository.SeatRepository;
import com.oop.springbootmvc.model.Ticket;
import com.oop.springbootmvc.repository.TicketRepository;
import com.oop.springbootmvc.viewmodel.BookingViewModel;
import com.oop.springbootmvc.viewmodel.RegisterViewModel;
import com.oop.springbootmvc.viewmodel.CancellationViewModel;
import com.oop.springbootmvc.viewmodel.UserViewModel;
import com.oop.springbootmvc.viewmodel.TransactionViewModel;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private TicketRepository ticketRepository;

    //Update Transaction record, status = 'cancelled' (When user cancels a booking)
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable("id") long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent()) {
            System.out.println("s");
            Transaction existingTransaction = transaction.get();
            existingTransaction.setStatus("cancelled");
            transactionRepository.save(existingTransaction);
    

            return ResponseEntity.ok(existingTransaction);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
        // cutomer's past bookings and option to cancel
    @GetMapping("/getTransactions/{custID}")
    public ResponseEntity<List<Map<String, Object>>> getTransactionsByCustomerId(@PathVariable("custID") long customerId) {


        // have to add if customerId !=  session id maybe? error to prevent users from accessing others transactions.
        // List<Transaction> transactions = transactionRepository.findByCustomerId(customerId);
        // Collections.sort(transactions, Comparator.comparingLong(Transaction::getId).reversed());

        List<Object[]> list = transactionRepository.findTransactionsByCustId(customerId);
        List<Map<String, Object>> transactions = new ArrayList<>();

        for (Object[] row : list) {
            Map<String, Object> rowMap = new HashMap<>();
            // add noOfTickets
            long transactionId = (int) row[0];
            List<Object[]> tickets = transactionRepository.findTicketsByTransactionId(transactionId);
            int ticketCount = 0;
            for (Object[] ticket : tickets) {
                ticketCount ++;
            }
            //end
            rowMap.put("id", row[0]);
            rowMap.put("status", row[1]);
            rowMap.put("customerId", row[2]);
            rowMap.put("sitId", row[3]);
            rowMap.put("purchasedDateTime", row[4]);
            rowMap.put("cost", row[5]);
            rowMap.put("eventName", row[6]);
            rowMap.put("eventId", row[7]);
            rowMap.put("eventStartDate", row[8]);
            rowMap.put("eventStartTime", row[9]);   
            rowMap.put("noSits", row[10]);
            rowMap.put("userCancellationCost", row[11]);
            rowMap.put("eventCancellationCost", row[12]);
            rowMap.put("noTickets", ticketCount);
            transactions.add(rowMap);
        }
        // Sort the list by the "id" key
        Collections.sort(transactions, Comparator.comparingInt(map -> (int) map.get("id")));
        Collections.reverse(transactions);
        return ResponseEntity.ok().body(transactions);
    }

    @GetMapping("/getTickets/{transactionID}")
    public ResponseEntity<List<Map<String, Object>>> getTicketsByTransactionId(@PathVariable("transactionID") long transactionId) {


        // have to add if customerId !=  session id maybe? error to prevent users from accessing others transactions.
        // List<Transaction> transactions = transactionRepository.findByCustomerId(customerId);
        // Collections.sort(transactions, Comparator.comparingLong(Transaction::getId).reversed());

        List<Object[]> list = transactionRepository.findTicketsByTransactionId(transactionId);
        List<Map<String, Object>> tickets = new ArrayList<>();

        for (Object[] row : list) {
            Map<String, Object> rowMap = new HashMap<>();
            rowMap.put("guid", row[0]);
            rowMap.put("status", row[1]);
            rowMap.put("seatType", row[2]);
            rowMap.put("eventName", row[3]);
            rowMap.put("startDate", row[4]);
            rowMap.put("startTime", row[5]);
            rowMap.put("endTime", row[6]);

            tickets.add(rowMap);
        }




        return ResponseEntity.ok().body(tickets);
    }

    @GetMapping("/getEventDetails/{transactionID}")
    public ResponseEntity<Map<String, Object>> getEventDetailsByTransactionId(@PathVariable("transactionID") long transactionId) {


        // have to add if customerId !=  session id maybe? error to prevent users from accessing others transactions.
        // List<Transaction> transactions = transactionRepository.findByCustomerId(customerId);
        // Collections.sort(transactions, Comparator.comparingLong(Transaction::getId).reversed());

        List<Object[]> list = transactionRepository.findTicketsByTransactionId(transactionId);
        Map<String, Object> rowMap = new HashMap<>();
        for (Object[] row : list) {
            

            rowMap.put("eventName", row[3]);
            rowMap.put("startDate", row[4]);
            rowMap.put("startTime", row[5]);
            rowMap.put("endTime", row[6]);

            break;
        }




        return ResponseEntity.ok().body(rowMap);
    }

    @PostMapping("/createTransaction")
    public void createTransaction(@RequestBody TransactionViewModel transactionViewModel) {
        Transaction t = new Transaction(transactionViewModel.getStatus(),transactionViewModel.getCustomerId(),transactionViewModel.getSitId(),transactionViewModel.getPurchasedDateTime(),transactionViewModel.getCost(),transactionViewModel.getCancellationCost());

        transactionRepository.save(t);
     
    }

    @GetMapping("/latestTransaction")
    public ResponseEntity<Object> getLatestTransaction(){
        Object[] latest = transactionRepository.findLatestEntry();
        return ResponseEntity.ok().body(latest);
    }
}
    
  

