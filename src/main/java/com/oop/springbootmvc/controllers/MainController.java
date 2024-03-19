package com.oop.springbootmvc.controllers;

import com.oop.springbootmvc.model.CustomUserDetails;
import com.oop.springbootmvc.model.User;
import com.oop.springbootmvc.repository.EventRepository;
import com.oop.springbootmvc.service.EventService;
import com.oop.springbootmvc.model.Event;
import com.oop.springbootmvc.model.EventWithSits;
import com.oop.springbootmvc.model.Sit;
import com.oop.springbootmvc.repository.SitRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
public class MainController {

    private final EventRepository eventRepository;
    private final SitRepository sitRepository;

    @Autowired
    public MainController(EventRepository eventRepository, SitRepository sitRepository) {
        this.eventRepository = eventRepository;
        this.sitRepository = sitRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        try {
            Authentication authentication = (Authentication) principal;
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            model.addAttribute("eventName", "LOGGED IN");
        }catch(Exception e){

            model.addAttribute("eventName", "NOT LOGGED IN");
        }
        // this just means render index.html from static/ area
        return "index";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        try {
            Authentication authentication = (Authentication) principal;
            User user = (User) authentication.getPrincipal();
            return "index";
        }catch(Exception e){
            return "register";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        try {
            Authentication authentication = (Authentication) principal;
            User user = (User) authentication.getPrincipal();
            return "index";
        }catch(Exception e){
            return "login";
        }
    }

    //Protected route
    @RequestMapping(value = "/custViewProfile", method = RequestMethod.GET)
    public String custViewProfile(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        // Authentication authentication = (Authentication) principal;
        // CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return "custViewProfile";
    }

    @RequestMapping(value = "/custViewEvents", method = RequestMethod.GET)
    public String custViewEvents(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        // Authentication authentication = (Authentication) principal;
        // CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return "custViewEvents";
    }

    @RequestMapping(value = "/custViewBookingHistory", method = RequestMethod.GET)
    public String custViewBookingHistory(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        // Authentication authentication = (Authentication) principal;
        // CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return "custViewBookingHistory";
    }

    @RequestMapping(value = "/custViewBookingDetails", method = RequestMethod.GET)
    public String custViewBookingDetails(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        // Authentication authentication = (Authentication) principal;
        // CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return "custViewBookingDetails";
    }

    // ==================================================== Ticketing Officer ====================================================
    @RequestMapping(value = "/ticOffViewEvents", method = RequestMethod.GET)
    public String ticOffViewEvents(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        
        // Authentication authentication = (Authentication) principal;
        // CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return "ticOffViewEvents";
    }

    @RequestMapping(value = "/ticOffVerifyTickets", method = RequestMethod.GET)
    public String ticOffVerifyTickets(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable

        // Authentication authentication = (Authentication) principal;
        // CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return "ticOffVerifyTickets";
    }

    @RequestMapping(value = "/ticOffPurchaseTickets", method = RequestMethod.GET)
    public String ticOffPurchaseTickets(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        
        // Authentication authentication = (Authentication) principal;
        // CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return "ticOffPurchaseTickets";
    }

    // ==================================================== Event Manager ====================================================
    @RequestMapping(value = "/eventManViewEvents", method = RequestMethod.GET)
    public String eventManViewEvents(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        
        // Authentication authentication = (Authentication) principal;
        // CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return "eventManViewEvents";
    }

    @GetMapping("/api/activeEvents")
    public ResponseEntity<List<EventWithSits>> getActiveEvents() {
        List<Event> activeEvents = eventRepository.findActiveEvents();
        List<EventWithSits> eventWithSitsList = new ArrayList<>();
        for (Event event : activeEvents) {
            List<Sit> seats = sitRepository.findSitByEventId(event.getId());
            EventWithSits response = new EventWithSits(activeEvents, seats);
            eventWithSitsList.add(response);
        }
        return ResponseEntity.ok(eventWithSitsList);
    }

    @GetMapping("/api/allEvents")
    public ResponseEntity<List<EventWithSits>> getAllEvents() {
        List<Event> events = eventRepository.findAllEvents();
        List<EventWithSits> eventWithSitsList = new ArrayList<>();
        for (Event event : events) {
            List<Sit> seats = sitRepository.findSitByEventId(event.getId());
            EventWithSits response = new EventWithSits(events, seats);
            eventWithSitsList.add(response);
        }
        return ResponseEntity.ok(eventWithSitsList);
    }

    // @GetMapping("/api/allEvents")
    // public ResponseEntity<List<Event>> getAllEvents() {
    //     List<Event> events = eventRepository.findAllEvents();
    //     return ResponseEntity.ok(events);
    // }

    
    @RequestMapping(value = "/eventManCreateEvent", method = RequestMethod.GET)
    public String eventManCreateEvent(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        
        // Authentication authentication = (Authentication) principal;
        // CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return "eventManCreateEvent";
    }

    // @RequestMapping(value = "/eventManEditEvent", method = RequestMethod.GET)
    // public String eventManEditEvent(Principal principal) {
    //     // this attribute will be available in the view index.html as a thymeleaf variable
    //     return "eventManEditEvent";
    // }

    @RequestMapping(value = "/eventManEditEvent/{eventId}", method = RequestMethod.GET)
    public String eventManEditEvent(@PathVariable("eventId") int eventId, Model model, Principal principal) {
        // Optional<Event> eventOptional = eventRepository.findById(eventId);
        // if (eventOptional.isPresent()) {
        //     Event event = eventOptional.get();
        //     model.addAttribute("eventData", event);
        //     return "eventManEditEvent";
        // } else {
        //     // Handle case where event with given ID is not found
        //     return "error";
        // }
        return "eventManEditEvent";
    }


    @RequestMapping(value = "/eventManViewTicOfficer", method = RequestMethod.GET)
    public String eventManViewTicOfficer(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        
        // Authentication authentication = (Authentication) principal;
        // CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return "eventManViewTicOfficer";
    }

    // @GetMapping("/viewEvents")
    // public String viewEvents(Model model) {
    //     List<Event> activeEvents = eventRepository.findActiveEvents();
    //     model.addAttribute("activeEvents", activeEvents);
    //     return "viewEvents";
    // }

    @RequestMapping(value = "/eventManViewDashboard", method = RequestMethod.GET)
    public String eventManViewDashboard(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        
        // Authentication authentication = (Authentication) principal;
        // CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        
        return "eventManViewDashboard";
    }

    
}
