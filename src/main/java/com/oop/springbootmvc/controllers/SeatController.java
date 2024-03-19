package com.oop.springbootmvc.controllers;

import com.oop.springbootmvc.model.Event;
import com.oop.springbootmvc.model.Seat;
import com.oop.springbootmvc.repository.EventRepository;
import com.oop.springbootmvc.repository.SeatRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class SeatController {
  private final SeatRepository seatRepository;
  private final EventRepository eventRepository;

  public SeatController(SeatRepository seatRepository, EventRepository eventRepository) {
    this.eventRepository = eventRepository;
    this.seatRepository = seatRepository;
  }

  @RequestMapping(value = "/api/getSeatByEventId/{eventId}", method = RequestMethod.GET)
  public ResponseEntity<Object> getSeatByEventId(@PathVariable("eventId") int eventId, Model model, Principal principal) {
    try {

      Optional<Event> event = eventRepository.findById(eventId);
      if (event.isEmpty()){
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Unable to retrieve event seat details");
        responseBody.put("status", "400");
        return ResponseEntity.ok().body(responseBody);
      }else{
        List<Seat> listSits = new ArrayList<>();
        listSits.addAll(event.get().getSeats());
        return ResponseEntity.ok().body(listSits);
      }
    
    } catch (Exception e) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Unable to retrieve event seat details");
        responseBody.put("status", "400");
        return ResponseEntity.ok().body(responseBody);
    }
  }
}