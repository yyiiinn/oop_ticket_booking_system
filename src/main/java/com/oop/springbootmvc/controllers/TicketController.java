package com.oop.springbootmvc.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oop.springbootmvc.model.Ticket;
import com.oop.springbootmvc.repository.TicketRepository;
import com.oop.springbootmvc.service.TicketService;
import com.oop.springbootmvc.viewmodel.GuidViewModel;

@RestController
public class TicketController {
    private final TicketRepository ticketRepository;
    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;     
    }
    
    @RequestMapping(value = "/api/officer/redeemCode", method = RequestMethod.POST)
    public ResponseEntity<Object> redeemCode(@RequestBody GuidViewModel guidViewModel) {
        try {
            Optional<Ticket> t = ticketRepository.findByGuid(guidViewModel.getGuid());
            if(t.isPresent()){
                Ticket tic = t.get();
                System.out.println(guidViewModel.getGuid());
                if (Objects.equals(tic.getStatus(), "Usable")){
                    tic.setStatus("Redeemed");
                    ticketRepository.save(tic);
                    return ResponseEntity.ok().body("");
                }  
            }
            return ResponseEntity.notFound().build();

        } catch (Exception e) {
            e.printStackTrace(); 
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "Failed to update event: " + e.getMessage());
            responseBody.put("status", "500");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Autowired
    private TicketService ticketService;

    @GetMapping("/api/manager/ViewDashboard/ticketSold/{eventId}")
    public ResponseEntity<Integer> countTicketsByEventId(@PathVariable int eventId) {
        int count = ticketService.countTicketsByEventId(eventId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/api/manager/ViewDashboard/customerAttendance/{status}/{eventId}")
    public ResponseEntity<Integer> customerAttendanceByEventID(@PathVariable String status, @PathVariable int eventId) {
        int count = ticketService.customerAttendanceByEventID(status, eventId);
        return ResponseEntity.ok(count);
    }

}
