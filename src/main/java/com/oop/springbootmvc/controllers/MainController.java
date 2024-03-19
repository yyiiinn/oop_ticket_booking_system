package com.oop.springbootmvc.controllers;

import com.oop.springbootmvc.model.CustomUserDetails;
import com.oop.springbootmvc.model.User;
import com.oop.springbootmvc.repository.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;


@Controller
public class MainController {


    @Autowired
    public MainController(EventRepository eventRepository) {
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
    @RequestMapping(value = "/customer/ViewProfile", method = RequestMethod.GET)
    public String custViewProfile(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        // Authentication authentication = (Authentication) principal;
        // CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return "custViewProfile";
    }

    @RequestMapping(value = "/customer/ViewEvents", method = RequestMethod.GET)
    public String custViewEvents(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        // Authentication authentication = (Authentication) principal;
        // CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return "custViewEvents";
    }

    @RequestMapping(value = "/customer/ViewBookingHistory", method = RequestMethod.GET)
    public String custViewBookingHistory(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        // Authentication authentication = (Authentication) principal;
        // CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return "custViewBookingHistory";
    }

    @RequestMapping(value = "/customer/ViewBookingDetails", method = RequestMethod.GET)
    public String custViewBookingDetails(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        // Authentication authentication = (Authentication) principal;
        // CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return "custViewBookingDetails";
    }

    // ==================================================== Ticketing Officer ====================================================
    @RequestMapping(value = "/officer/ViewEvents", method = RequestMethod.GET)
    public String ticOffViewEvents(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        
        // Authentication authentication = (Authentication) principal;
        // CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return "ticOffViewEvents";
    }

    @RequestMapping(value = "/officer/VerifyTickets", method = RequestMethod.GET)
    public String ticOffVerifyTickets(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable

        // Authentication authentication = (Authentication) principal;
        // CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return "ticOffVerifyTickets";
    }

    @RequestMapping(value = "/officer/PurchaseTickets", method = RequestMethod.GET)
    public String ticOffPurchaseTickets(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        
        // Authentication authentication = (Authentication) principal;
        // CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return "ticOffPurchaseTickets";
    }

    // ==================================================== Event Manager ====================================================
    @RequestMapping(value = "/manager/ViewEvents", method = RequestMethod.GET)
    public String eventManViewEvents(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        
        // Authentication authentication = (Authentication) principal;
        // CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return "eventManViewEvents";
    }

    
    @RequestMapping(value = "/manager/CreateEvent", method = RequestMethod.GET)
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

    @RequestMapping(value = "/manager/EditEvent/{eventId}", method = RequestMethod.GET)
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


    @RequestMapping(value = "/manager/ViewTicOfficer", method = RequestMethod.GET)
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

    @RequestMapping(value = "/manager/ViewDashboard", method = RequestMethod.GET)
    public String eventManViewDashboard(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        
        // Authentication authentication = (Authentication) principal;
        // CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        
        return "eventManViewDashboard";
    }

    
}
