package com.oop.springbootmvc.controllers;

import com.oop.springbootmvc.model.Event;
import com.oop.springbootmvc.model.TestObj;
import com.oop.springbootmvc.model.User;
import com.oop.springbootmvc.repository.TestObjRepository;
import com.oop.springbootmvc.repository.EventRepository;
import com.oop.springbootmvc.viewmodel.LoginViewModel;
import com.oop.springbootmvc.viewmodel.RegisterViewModel;
import com.oop.springbootmvc.viewmodel.UserViewModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
public class EventController {
  private final EventRepository eventRepository;

  public EventController(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

//   @RequestMapping(value = "/viewEvents", method = RequestMethod.GET)
//   public String viewEvents(Model model) {
//         List<Event> activeEvents = eventRepository.findActiveEvents();
//         model.addAttribute("activeEvents", activeEvents);
//         return "viewEvents";
//   }

}
