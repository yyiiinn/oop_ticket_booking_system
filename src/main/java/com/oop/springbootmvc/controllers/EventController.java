package com.oop.springbootmvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oop.springbootmvc.model.Event;
import com.oop.springbootmvc.model.TestObj;
import com.oop.springbootmvc.model.User;
import com.oop.springbootmvc.repository.TestObjRepository;
import com.oop.springbootmvc.repository.EventRepository;
import com.oop.springbootmvc.viewmodel.EventViewModel;
import com.oop.springbootmvc.viewmodel.LoginViewModel;
import com.oop.springbootmvc.viewmodel.RegisterViewModel;
import com.oop.springbootmvc.viewmodel.UserViewModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class EventController {
  private final EventRepository eventRepository;

  public EventController(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  @PostMapping("/api/createEvent")
  public ResponseEntity<Object> createEvent(@RequestBody EventViewModel eventViewModel) {  
      try {
          System.out.println("abcdeee");
          System.out.println(eventViewModel);
          System.out.println(eventViewModel.getEventStartTime());
          System.out.println(eventViewModel.getSalesEndTime());
          Event event = new Event(eventViewModel.getEventName(), eventViewModel.getEventDescription(), eventViewModel.getEventVenue(), 
          eventViewModel.geEventImageFile(), eventViewModel.getEventDate(), eventViewModel.getEventDate(), eventViewModel.getEventStartTime(), 
          eventViewModel.getEventEndTime(), eventViewModel.getSalesStartTime(), eventViewModel.getSalesEndTime(), 
          "Active");
          Event createdEvent = eventRepository.save(event);
          Map<String, String> responseBody = new HashMap<>();
          responseBody.put("message", "Event created successfully");
          responseBody.put("status", "200");
          return ResponseEntity.ok().body(responseBody);
      } catch (Exception e) {
          e.printStackTrace();
          Map<String, String> responseBody = new HashMap<>();
          responseBody.put("message", "Failed to Create Event: " + e.getMessage());
          responseBody.put("status", "400");
          return ResponseEntity.ok().body(responseBody);
      }
  }

  @RequestMapping(value = "/api/getEventDetails/{eventId}", method = RequestMethod.GET)
  public ResponseEntity<Object> getEventDetails(@PathVariable("eventId") Long eventId, Model model, Principal principal) {
    try {
      Optional<Event> eventOptional = eventRepository.findById(eventId);
      if (eventOptional.isPresent()) {
          Event event = eventOptional.get();
          return ResponseEntity.ok().body(event);
      } else {
          // Handle case where event with given ID is not found
          Map<String, String> responseBody = new HashMap<>();
          responseBody.put("message", "No event details found");
          responseBody.put("status", "202");
          return ResponseEntity.ok().body(responseBody);
      }
    } catch (Exception e) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Unable to retrieve event details");
        responseBody.put("status", "400");
        return ResponseEntity.ok().body(responseBody);
    }
  }

  @PostMapping("/api/submitEditEvent/{eventId}")
  public ResponseEntity<Object> submitEditEvent(@PathVariable("eventId") Long eventId, @RequestBody EventViewModel eventViewModel) {
    try {
        System.out.println("aabbcc");
        // Find the existing event by ID
        Optional<Event> optionalEvent = eventRepository.findById((long) 23);
        
        if (!optionalEvent.isPresent()) {
            // Event not found, return 404 status
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "Event with ID " + eventId + " not found");
            responseBody.put("status", "404");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
        
        Event existingEvent = optionalEvent.get();
        
        // Update event details from the view model
        existingEvent.setName(eventViewModel.getEventName());
        existingEvent.setDescription(eventViewModel.getEventDescription());
        existingEvent.setVenue(eventViewModel.getEventVenue());
        existingEvent.setImageUrl(eventViewModel.geEventImageFile());
        existingEvent.setEventStartDate(eventViewModel.getEventDate());
        existingEvent.setEventEndDate(eventViewModel.getEventDate());
        existingEvent.setEventStartTime(eventViewModel.getEventStartTime());
        existingEvent.setEventEndTime(eventViewModel.getEventEndTime());
        existingEvent.setTicketSaleStartTime(eventViewModel.getSalesStartTime()); // Fixed method name
        existingEvent.setTicketSaleEndTime(eventViewModel.getSalesEndTime());
        
        // Save the updated event
        Event updatedEvent = eventRepository.save(existingEvent);
        
        // Return success response
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Event updated successfully");
        responseBody.put("status", "200");
        return ResponseEntity.ok().body(responseBody);
    } catch (Exception e) {
        // Log and return error response
        e.printStackTrace(); // Log the exception
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Failed to update event: " + e.getMessage());
        responseBody.put("status", "500"); // Use appropriate error status
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }
  }



//   @RequestMapping(value = "/viewEvents", method = RequestMethod.GET)
//   public String viewEvents(Model model) {
//         List<Event> activeEvents = eventRepository.findActiveEvents();
//         model.addAttribute("activeEvents", activeEvents);
//         return "viewEvents";
//   }

}