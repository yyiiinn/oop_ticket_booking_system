package com.oop.springbootmvc.controllers;

import com.oop.springbootmvc.service.SeatService;

import com.oop.springbootmvc.model.Event;
import com.oop.springbootmvc.model.Seat;
import com.oop.springbootmvc.model.Ticket;
import com.oop.springbootmvc.model.Transaction;
import com.oop.springbootmvc.model.User;
import com.oop.springbootmvc.repository.EventRepository;
import com.oop.springbootmvc.repository.TicketRepository;
import com.oop.springbootmvc.repository.TransactionRepository;
import com.oop.springbootmvc.repository.UserRepository;
import com.oop.springbootmvc.viewmodel.CustomerEventViewModel;
import com.oop.springbootmvc.viewmodel.EventOnlyViewModel;
import com.oop.springbootmvc.viewmodel.EventViewModel;
import com.oop.springbootmvc.viewmodel.SeatViewModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class EventController {
  private final EventRepository eventRepository;
  private final TicketRepository ticketRepository;
  private final TransactionRepository transactionRepository;
  private final UserRepository userRepository;

  @Autowired
  private SeatService seatService;

  public EventController(EventRepository eventRepository, TicketRepository ticketRepository, TransactionRepository transactionRepository, UserRepository userRepository) {
    this.eventRepository = eventRepository;
    this.ticketRepository = ticketRepository;
    this.transactionRepository = transactionRepository;
    this.userRepository = userRepository;

  }

  @GetMapping("/api/activeEvents")
  public ResponseEntity<Object> getActiveEvents() {
    List<Event> activeEvents = eventRepository.findActiveEvents();
    ArrayList<CustomerEventViewModel> toReturn = new ArrayList<>();
    for (Event event : activeEvents) {
      // Get All Seats
      var seats = event.getSeats();
      for (Seat s :seats){
        int booked = 0;
        // Get All Transactions
        for (Transaction t: s.getTransactions()){
          if(t.getStatus().equals("Booked")){
            booked += t.getTickets().size();
          }
        };
        s.setNumberOfSeats(s.getNumberOfSeats() - booked);
      }
      event.setSeats(seats);
      toReturn.add(new CustomerEventViewModel(event));
    }
    
    return ResponseEntity.ok(toReturn);
  }

  @GetMapping("/api/allEvents")
  public ResponseEntity<Object> getAllEvents() {
      List<Event> events = eventRepository.findAllEvents();
      ArrayList<EventOnlyViewModel> toReturn = new ArrayList<>();
      for (Event event : events) {
        toReturn.add(new EventOnlyViewModel(event));
      }
      return ResponseEntity.ok(toReturn);
  }
  
  @PostMapping("/api/manager/createEvent")
  public ResponseEntity<Object> createEvent(@RequestBody EventViewModel eventViewModel) {  
      try {
          Event event = new Event(eventViewModel.getEventName(), eventViewModel.getEventDescription(), eventViewModel.getEventVenue(), 
          eventViewModel.geEventImageFile(), eventViewModel.getEventStartDate(), eventViewModel.getEventEndDate(), eventViewModel.getEventStartTime(), 
          eventViewModel.getEventEndTime(), eventViewModel.getSalesStartTime(), eventViewModel.getSalesEndTime(), 
          "Active", eventViewModel.getEventCategory(), eventViewModel.getCancellationFee());
          Event createdEvent = eventRepository.save(event);
          List<SeatViewModel> sitViewModels = eventViewModel.getSeatingOptions();
          boolean seatsAdded = false;
          if (createdEvent != null){
            seatsAdded = seatService.createSeatsByEvent(createdEvent, sitViewModels);
          }
          if (seatsAdded == true) {
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "Event created successfully");
            responseBody.put("status", "200");
            return ResponseEntity.ok().body(responseBody);
          }
          Map<String, String> responseBody = new HashMap<>();
          responseBody.put("message", "Unable to create event");
          responseBody.put("status", "400");
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
  public ResponseEntity<Object> getEventDetails(@PathVariable("eventId") int eventId, Model model, Principal principal) {
    try {
      Optional<Event> eventOptional = eventRepository.findById(eventId);
      if (eventOptional.isPresent()) {
          Event event = eventOptional.get();
          List<Seat> seats = new ArrayList<>();
          seats.addAll(event.getSeats());
          Map<String, Object> responseMap = new HashMap<>();
          responseMap.put("eventDetails", event);
          responseMap.put("seats", seats);
          return ResponseEntity.ok().body(responseMap);
      } else {
          Map<String, String> responseBody = new HashMap<>();
          responseBody.put("message", "No event details found");
          // Please do not set status like this
          // responseBody.put("status", "202");
          // Please set it as such
          return ResponseEntity.status(202).body(responseBody);
      }
    } catch (Exception e) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Unable to retrieve event details");
        responseBody.put("status", "400");
        return ResponseEntity.ok().body(responseBody);
    }
  }

  @PostMapping("/api/manager/submitEditEvent/{eventId}")
  public ResponseEntity<Object> submitEditEvent(@PathVariable("eventId") int eventId, @RequestBody EventViewModel eventViewModel) {
    try {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (!optionalEvent.isPresent()) {
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "Event with ID " + eventId + " not found");
            responseBody.put("status", "404");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }

        Event existingEvent = optionalEvent.get();
        
        existingEvent.setName(eventViewModel.getEventName());
        existingEvent.setDescription(eventViewModel.getEventDescription());
        existingEvent.setVenue(eventViewModel.getEventVenue());
        existingEvent.setImage(eventViewModel.geEventImageFile());
        existingEvent.setEventStartDate(eventViewModel.getEventStartDate());
        existingEvent.setEventEndDate(eventViewModel.getEventStartDate());
        existingEvent.setEventStartTime(eventViewModel.getEventStartTime());
        existingEvent.setEventEndTime(eventViewModel.getEventEndTime());
        existingEvent.setTicketSaleStartTime(eventViewModel.getSalesStartTime());
        existingEvent.setTicketSaleEndTime(eventViewModel.getSalesEndTime());
        existingEvent.setEventCategory(eventViewModel.getEventCategory());
        existingEvent.setCancellationFee(eventViewModel.getCancellationFee());

        Event updatedEvent = eventRepository.save(existingEvent);
        boolean seatsAdded = false;
        if (updatedEvent != null) {
          long event_id = updatedEvent.getId();
          seatsAdded = seatService.createSeatsByEvent(updatedEvent, eventViewModel.getSeatingOptions());
        }
        Map<String, String> responseBody = new HashMap<>();
        if (seatsAdded == true) {
          responseBody.put("message", "Event updated successfully");
          responseBody.put("status", "200");
          return ResponseEntity.ok().body(responseBody);
        }
        responseBody.put("message", "Unable to update Event and Seating Options");
        responseBody.put("status", "400");
        return ResponseEntity.ok().body(responseBody);
    } catch (Exception e) {
        e.printStackTrace(); 
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Failed to update event: " + e.getMessage());
        responseBody.put("status", "500");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }
  }

  @PostMapping("/api/manager/cancelEvent/{eventId}")
    public ResponseEntity<Object> cancelEvent(@PathVariable("eventId") int eventId, @RequestBody EventViewModel eventViewModel) {
      try {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (!optionalEvent.isPresent()) {
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "Event with ID " + eventId + " not found");
            responseBody.put("status", "404");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
        Event existingEvent = optionalEvent.get();

        LocalDateTime now = LocalDateTime.now();
        LocalDate localDate = existingEvent.getEventStartDate().toLocalDate();
        LocalTime localTime = existingEvent.getEventStartTime().toLocalTime();
        LocalDateTime eventStartDateTime = LocalDateTime.of(localDate, localTime);
        long daysUntilEvent = ChronoUnit.DAYS.between(now, eventStartDateTime);
        if (daysUntilEvent >= 2) {
          existingEvent.setStatus("Cancelled");
          Event updatedEvent = eventRepository.save(existingEvent);
          // TO TRIGGER AUTOMATED REFUND
          for(Seat s:updatedEvent.getSeats()){
            for(Transaction t: s.getTransactions()){
              if(!t.getStatus().equals("Cancelled")){
                // We shall set refund status
                t.setStatus("Refunded");
                for(Ticket tic: t.getTickets()){
                  tic.setStatus("Cancelled");
                  ticketRepository.save(tic);
                }
                transactionRepository.save(t);
                User u = t.getUser();
                u.setBalance(u.getBalance()+t.getCost());
                userRepository.save(u);
              }
            }
          }

          Map<String, String> responseBody = new HashMap<>();
          responseBody.put("message", "Event cancelled successfully");
          responseBody.put("status", "200");
          return ResponseEntity.ok().body(responseBody);
        } else {
          Map<String, String> responseBody = new HashMap<>();
          responseBody.put("message", "Unable to cancel event as event is starting in less than 2 days");
          responseBody.put("status", "400");
          return ResponseEntity.ok().body(responseBody);
        }
    } catch (Exception e) {
        e.printStackTrace(); 
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Failed to cancel event: " + e.getMessage());
        responseBody.put("status", "500"); // Use appropriate error status
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }
    }




}