package com.oop.springbootmvc.controllers;

import com.oop.springbootmvc.model.CustomUserDetails;
import com.oop.springbootmvc.model.User;
import com.oop.springbootmvc.repository.EventRepository;
import com.oop.springbootmvc.service.EventService;
import com.oop.springbootmvc.model.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
public class MainController {

    private final EventRepository eventRepository;

    @Autowired
    public MainController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
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
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        Authentication authentication = (Authentication) principal;
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return "profile";
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

    @RequestMapping(value = "/eventManCreateEvent", method = RequestMethod.GET)
    public String eventManCreateEvent(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        
        // Authentication authentication = (Authentication) principal;
        // CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return "eventManCreateEvent";
    }

    @RequestMapping(value = "/eventManEditEvent", method = RequestMethod.GET)
    public String eventManEditEvent(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        
        // Authentication authentication = (Authentication) principal;
        // CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return "eventManEditEvent";
    }

    @RequestMapping(value = "/eventManViewTicOfficer", method = RequestMethod.GET)
    public String eventManViewTicOfficer(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        
        // Authentication authentication = (Authentication) principal;
        // CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return "eventManViewTicOfficer";
    }

    @GetMapping("/viewEvents")
    public String viewEvents(Model model) {
        List<Event> activeEvents = eventRepository.findActiveEvents();
        model.addAttribute("activeEvents", activeEvents);
        return "viewEvents";
    }
    
}
