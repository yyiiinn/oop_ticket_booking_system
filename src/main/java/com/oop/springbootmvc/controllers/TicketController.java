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

import com.oop.springbootmvc.model.Event;
import com.oop.springbootmvc.model.Ticket;
import com.oop.springbootmvc.repository.EventRepository;
import com.oop.springbootmvc.repository.TicketRepository;
import com.oop.springbootmvc.service.TicketService;
import com.oop.springbootmvc.viewmodel.GuidViewModel;

@RestController
public class TicketController {
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;

    public TicketController(TicketRepository ticketRepository, EventRepository eventRepository) {
        this.ticketRepository = ticketRepository;     
        this.eventRepository = eventRepository;
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
    public ResponseEntity<Object> countTicketsByEventId(@PathVariable(required = false) Integer eventId) {
        try {
            int countTickets;
            if (eventId != null && eventId != 0) {
                countTickets = ticketService.countTicketsByEventId(eventId);
            } else {
                countTickets = ticketService.totalTicketsSold();
            }
            return ResponseEntity.ok(countTickets);
        } catch (Exception e) {
            return ResponseEntity.status(403).body("");
        }
    }

    @GetMapping("/api/manager/ViewDashboard/customerAttendance/{eventId}")
    public ResponseEntity<Object> customerAttendanceByEventID(@PathVariable int eventId) {
        try {
            Optional<Event> optionalEvent = eventRepository.findById(eventId);
            Map<String, Object> jsonResponse = new HashMap<>();
            if (optionalEvent.isPresent()){
                int customerAttendance = ticketService.customerAttendanceByEventID("Redeemed", eventId);
                int customerCancelled = ticketService.customerAttendanceByEventID("Cancelled", eventId);
                int customerUattended = ticketService.customerAttendanceByEventID("Usable", eventId);
                jsonResponse.put("Attended", customerAttendance);
                jsonResponse.put("Cancelled", customerCancelled);
                jsonResponse.put("Unattended", customerUattended);
            } else {
                int customerAttendance = ticketService.customerAttendanceByStatus("Redeemed");
                int customerCancelled = ticketService.customerAttendanceByStatus("Cancelled");
                int customerUattended = ticketService.customerAttendanceByStatus("Usable");
                jsonResponse.put("Attended", customerAttendance);
                jsonResponse.put("Cancelled", customerCancelled);
                jsonResponse.put("Unattended", customerUattended);
            }
            return ResponseEntity.ok().body(jsonResponse);
        } catch (Exception e) {
            return ResponseEntity.status(403).body("");
        }
    }

}
