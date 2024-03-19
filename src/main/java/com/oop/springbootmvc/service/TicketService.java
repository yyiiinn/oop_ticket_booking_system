package com.oop.springbootmvc.service;

import com.oop.springbootmvc.model.CustomUserDetails;
import com.oop.springbootmvc.model.User;
import com.oop.springbootmvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

import com.oop.springbootmvc.model.Ticket;
import com.oop.springbootmvc.repository.TicketRepository;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    
    @Autowired
    private TicketRepository ticketRepository;

    public void updateTicket(Ticket updatedTicket) {
        // Assuming transactionId is the field that needs to be updated
        Ticket existingTicket = ticketRepository.findById(updatedTicket.getId()).orElse(null);
        if (existingTicket != null) {
            existingTicket.setStatus("cancelled");
            ticketRepository.save(existingTicket);
        }
    }

}