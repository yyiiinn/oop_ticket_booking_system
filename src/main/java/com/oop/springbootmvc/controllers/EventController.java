package com.oop.springbootmvc.controllers;

import org.springframework.stereotype.Controller;

import com.oop.springbootmvc.model.Transaction;
import com.oop.springbootmvc.model.User;
import com.oop.springbootmvc.repository.TransactionRepository;
import com.oop.springbootmvc.repository.UserRepository;
import com.oop.springbootmvc.model.Event;
import com.oop.springbootmvc.repository.EventRepository;
import com.oop.springbootmvc.model.Sit;
import com.oop.springbootmvc.repository.SitRepository;



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
@RequestMapping("/api/event")
public class EventController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private SitRepository sitRepository;
    @Autowired
    private EventRepository eventRepository;


    @GetMapping("/getViewEvents")
    public ResponseEntity<List<Map<String, Object>>> getViewEvents() {
     // Fetch user by the given user ID
     List<Event> eventList = eventRepository.findAll();
     List<Map<String, Object>> viewEvents = new ArrayList<>();

     for (Event e : eventList){
        Map<String, Object> rowMap = new HashMap<>();
        List<Sit> tempList = sitRepository.findByEventId(e.getId());
        int noOfTickets = 0;
        for (Sit s : tempList){
            noOfTickets+= s.getNumberOfSits();
        }
        rowMap.put("id", e.getId());
        rowMap.put("name", e.getName());
        rowMap.put("desc", e.getDescription());
        rowMap.put("date", e.getEventStartDate());
        rowMap.put("startTime", e.getEventStartTime());
        rowMap.put("endTime", e.getEventEndTime());
        rowMap.put("venue", e.getVenue());
        rowMap.put("availableTickets", noOfTickets);
        rowMap.put("imageUrl", e.getImageUrl());
        rowMap.put("status", e.getStatus());
        rowMap.put("saleStart", e.getTicketSaleStartDateTime());
        rowMap.put("saleEnd", e.getTicketSaleEndDateTime());
        rowMap.put("category", e.getCategory());
        rowMap.put("cancellationCost", e.getCancellationCost());
        rowMap.put("seatingOptions", tempList);
        viewEvents.add(rowMap);


     }
     return ResponseEntity.ok().body(viewEvents);
    }
}



