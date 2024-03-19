package com.oop.springbootmvc.controllers;

import org.springframework.stereotype.Controller;

import com.oop.springbootmvc.model.Transaction;
import com.oop.springbootmvc.model.User;
import com.oop.springbootmvc.repository.TransactionRepository;
import com.oop.springbootmvc.repository.UserRepository;
import com.oop.springbootmvc.viewmodel.TicketViewModel;
import com.oop.springbootmvc.viewmodel.TransactionViewModel;
import com.oop.springbootmvc.model.Event;
import com.oop.springbootmvc.repository.EventRepository;
import com.oop.springbootmvc.model.Sit;
import com.oop.springbootmvc.model.Ticket;
import com.oop.springbootmvc.repository.SitRepository;
import com.oop.springbootmvc.repository.TicketRepository;
import com.oop.springbootmvc.service.TicketService;


import jakarta.transaction.Transactional;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private SitRepository sitRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketService ticketService;

    @PutMapping("/cancelTicket/{id}")
    public void cancelTicket(@PathVariable("id") long id) {
        List<Ticket> tickets = ticketRepository.findByTransactionId(id);
        for (Ticket t : tickets) {
            ticketService.updateTicket(t);            
        }
     
    }    


    @PostMapping("/createTicket")
    public void createTicket(@RequestBody TicketViewModel ticketViewModel) {
        Ticket t = new Ticket(ticketViewModel.getGuid(),ticketViewModel.getTransactionId(),ticketViewModel.getStatus());

        ticketRepository.save(t);
     
    }


}