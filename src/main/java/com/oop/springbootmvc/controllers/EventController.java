package com.oop.springbootmvc.controllers;

import com.oop.springbootmvc.model.Event;
import com.oop.springbootmvc.model.Seat;
import com.oop.springbootmvc.repository.EventRepository;
import com.oop.springbootmvc.repository.SeatRepository;
import com.oop.springbootmvc.viewmodel.EventViewModel;
import com.oop.springbootmvc.viewmodel.SeatViewModel;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {
  private final EventRepository eventRepository;
  private final SeatRepository seatRepository;

  public EventController(EventRepository eventRepository, SeatRepository seatRepository) {
    this.eventRepository = eventRepository;
    this.seatRepository = seatRepository;

  }

  @GetMapping("/api/event/{id}")
  Optional<Event> one(@PathVariable("id") Long id) {
    return this.eventRepository.findById(id);
  }

  @PostMapping("/api/manager/event/create")
  public ResponseEntity<Object> createEvent(@RequestBody EventViewModel eventViewModel) {
    
    try {

      Event event = new Event();
      event.setName(eventViewModel.getName());
      event.setDescription(eventViewModel.getDescription());
      event.setVenue(eventViewModel.getVenue());
      event.setImageURL(eventViewModel.getImageURL());
      event.setCategory(eventViewModel.getCategory());
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
      DateFormat timeFormatter = new SimpleDateFormat("HH:mm");
      DateTimeFormatter dateTimeFormatter = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
      event.setEventStartDate(formatter.parse(eventViewModel.getEventStartDate()));
      event.setEventEndDate(formatter.parse(eventViewModel.getEventEndDate()));
      event.setEventStartTime(new Time(timeFormatter.parse(eventViewModel.getEventStartTime()).getTime()));
      event.setEventEndTime(new Time(timeFormatter.parse(eventViewModel.getEventEndTime()).getTime()));
      event.setTicketSaleStartDateTime(LocalDateTime.parse(eventViewModel.getTicketSaleStartDateTime(), dateTimeFormatter));
      event.setTicketSaleEndDateTime(LocalDateTime.parse(eventViewModel.getTicketSaleEndDateTime(), dateTimeFormatter));
      // I am lazy to do enum so we shall use string. Options available:
      // Draft, Published, Canceled
      event.setStatus(eventViewModel.getStatus());

      Event createdEvent = this.eventRepository.save(event);

      for (SeatViewModel s: eventViewModel.getSeats()) {
        Seat seat = new Seat();
        seat.setCost(s.getCost());
        seat.setNumberOfSits(s.getNumberOfSeats());
        seat.setType(s.getType());
        seat.setEvent(createdEvent);
        seat.setCancellationFee(s.getCancellationFee());
        this.seatRepository.save(seat);
      }

      return new ResponseEntity<>(HttpStatus.OK) ;
    }catch(Exception e){
      System.out.println(e);
      return new ResponseEntity<>("Failed to Create Event", HttpStatus.NOT_ACCEPTABLE) ;
    }
  }
//
//  @PostMapping("/api/register")
//  public TestObj add(@RequestBody User user) {
//    return this.testObjRepository.save(testObj);
//  }


}
