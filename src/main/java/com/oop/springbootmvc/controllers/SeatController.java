package com.oop.springbootmvc.controllers;

import com.oop.springbootmvc.model.Event;
import com.oop.springbootmvc.model.Seat;
import com.oop.springbootmvc.model.Transaction;
import com.oop.springbootmvc.repository.EventRepository;
import com.oop.springbootmvc.repository.SeatRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
      if (event.isPresent()){
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Unable to retrieve event seat details");
        responseBody.put("status", "400");
        return ResponseEntity.ok().body(responseBody);
      }else{
        List<Seat> listSeats = new ArrayList<>();
        listSeats.addAll(event.get().getSeats());
        return ResponseEntity.ok().body(listSeats);
      }
    
    } catch (Exception e) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Unable to retrieve event seat details");
        responseBody.put("status", "400");
        return ResponseEntity.ok().body(responseBody);
    }
  }

  @GetMapping(value = "/api/manager/ViewDashboard/seatsDetails/{eventId}")
  public ResponseEntity<Object> seatsDetailsByEventId(@PathVariable int eventId) {
      try {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isPresent()){
          Event event = optionalEvent.get();
          Set<Seat> seats = event.getSeats();
          event.setSeats(seats);
          DecimalFormat df = new DecimalFormat("#.##");
          Map<String, Object> jsonResponse = new HashMap<>();
          for (Seat s :seats){
            int totalSeatsBooked = 0;
            int totalSeatsLeft = 0;
            int totalSeatsRefunded = 0;
            int totalSeatsCancelled = 0;
            int initialSeats = s.getNumberOfSeats();
            for (Transaction t: s.getTransactions()){
              if(t.getStatus().equals("Booked")){
                totalSeatsBooked += t.getTickets().size();
              } else if (t.getStatus().equals("Refunded")) {
                totalSeatsRefunded += t.getTickets().size();
              } else if (t.getStatus().equals("Cancelled")) {
                totalSeatsCancelled += t.getTickets().size();
              }
            };
            totalSeatsLeft = s.getNumberOfSeats() - totalSeatsBooked - totalSeatsRefunded - totalSeatsCancelled;
            double bookedPercentage = (double) totalSeatsBooked / initialSeats * 100;
            double leftPercentage = (double) totalSeatsLeft / initialSeats * 100;
            double refundedPercentage = (double) totalSeatsRefunded / initialSeats * 100;
            double cancelledPercentage = (double) totalSeatsCancelled / initialSeats * 100;
            Map<String, Object> innerObject = new HashMap<>();
            innerObject.put("bookedPercentage", df.format(bookedPercentage));
            innerObject.put("leftPercentage", df.format(leftPercentage));
            innerObject.put("refundedPercentage", df.format(refundedPercentage));
            innerObject.put("cancelledPercentage", df.format(cancelledPercentage));
            innerObject.put("numberOfSeats", initialSeats);
            innerObject.put("type", s.getType());
            innerObject.put("cost", s.getCost());
            jsonResponse.put(s.getType(), List.of(innerObject));
          }
          return ResponseEntity.ok().body(jsonResponse);
        }else {
          return ResponseEntity.status(403).body("");
        }
      } catch (Exception e) {
          // TODO: handle exception
          return ResponseEntity.status(403).body("");
      }
  }
}