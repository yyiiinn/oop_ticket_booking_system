package com.oop.springbootmvc.controllers;

import com.oop.springbootmvc.model.Event;
import com.oop.springbootmvc.model.Seat;
import com.oop.springbootmvc.repository.EventRepository;
import com.oop.springbootmvc.repository.SeatRepository;
import com.oop.springbootmvc.viewmodel.BookingViewModel;
import com.oop.springbootmvc.viewmodel.CancellationViewModel;

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

      //UPDATE Sit record (add back number of seats) after
    @PutMapping("/addSits/{id}")
    public ResponseEntity<Seat> addSitCount(@PathVariable("id") long id, @RequestBody CancellationViewModel viewModel ) {
        Optional<Seat> seat = seatRepository.findById(id);
        if (seat.isPresent()) {
            Seat existingSeat = seat.get();
            existingSeat.setNumberOfSeats(existingSeat.getNumberOfSeats() + viewModel.getNumberOfSits());
            seatRepository.save(existingSeat);
            return ResponseEntity.ok(existingSeat);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/deductSits/{id}")
    public ResponseEntity<Seat> deductSitCount(@PathVariable("id") long id, @RequestBody BookingViewModel viewModel ) {
        Optional<Seat> sit = seatRepository.findById(id);
        if (sit.isPresent()) {
            Seat existingSeat = sit.get();
            existingSeat.setNumberOfSeats(existingSeat.getNumberOfSeats() - viewModel.getNoOfSits());
            seatRepository.save(existingSeat);
            return ResponseEntity.ok(existingSeat);
        } else {
            return ResponseEntity.notFound().build();
        }
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