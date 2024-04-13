package com.oop.springbootmvc.controllers;

import com.oop.springbootmvc.entities.RoleEnum;
import com.oop.springbootmvc.model.CustomUserDetails;
import com.oop.springbootmvc.model.Role;
import com.oop.springbootmvc.model.User;
import com.oop.springbootmvc.repository.EventRepository;
import com.oop.springbootmvc.repository.RoleRepository;
import com.oop.springbootmvc.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

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

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Autowired
    public MainController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET) 
    public String index(Model model, Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        try {
            Authentication authentication = (Authentication) principal;
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            Role r = roleRepository.findByName(RoleEnum.OFFICER).get();
            Role rm = roleRepository.findByName(RoleEnum.MANAGER).get();
            Role ru = roleRepository.findByName(RoleEnum.USER).get();

            if (user.getUser().getRole().getName() ==r.getName()){
                User u = userRepository.findById(user.getUser().getId()).get();
                if (!u.getHasPasswordChange()){
                    //Go to change password page
                    return "ticOffChangePassword";
                }else{
                    return "ticOffViewEvents";
                }
            }
            else if (user.getUser().getRole().getName() ==rm.getName()){
                return "eventManViewEvents";

            }
            else if (user.getUser().getRole().getName() ==ru.getName()){
                return "custViewEvents";

            }
            // model.addAttribute("eventName", "LOGGED IN");
        }catch(Exception e){

            // model.addAttribute("eventName", "NOT LOGGED IN");
        }
        // this just means render index.html from static/ area
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Principal principal) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        try {
            Authentication authentication = (Authentication) principal;
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            return "index";
        }catch(Exception e){
            return "register";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Principal principal, Model model, HttpServletRequest request) {
    // Check if the user is already authenticated
    try {
        Authentication authentication = (Authentication) principal;
        if (authentication != null && authentication.isAuthenticated()) {
            // If authenticated, redirect to index or another relevant page
            return "redirect:/"; // Or another relevant page
        }
    } catch (Exception e) {
        // If there's an exception, meaning the user is not authenticated, proceed to check for login errors.
    }

    // Check if there's a login error. You can also use @RequestParam here, but HttpServletRequest gives more flexibility.
    if (request.getParameter("error") != null) {
        model.addAttribute("loginError", "Invalid username or password. Please try again.");
    }

    return "login";
}

    //Protected route
    @RequestMapping(value = "/Customer/ViewProfile", method = RequestMethod.GET)
    public String custViewProfile(Model model, Principal principal) {
        try {
            Authentication authentication = (Authentication) principal;
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            User tempUser = user.getUser();
            User u = userRepository.findById(tempUser.getId()).get();
            model.addAttribute("username", u.getUsername());
            model.addAttribute("name", u.getName());
            model.addAttribute("balance", u.getBalance());

            return "custViewProfile";
        }catch(Exception e){
            return "login";
        }
    }

    @RequestMapping(value = "/Customer/ViewEvents", method = RequestMethod.GET)
    public String custViewEvents(Principal principal) {

        return "custViewEvents";
    }

    @RequestMapping(value = "/Customer/ViewBookingHistory", method = RequestMethod.GET)
    public String custViewBookingHistory(Principal principal) {
        

        return "custViewBookingHistory";
    }

    @RequestMapping(value = "/Customer/ViewBookingDetails/{transactionId}", method = RequestMethod.GET)
    public String custViewBookingDetails(@PathVariable("transactionId") int transactionId, Principal principal) {
        

        return "custViewBookingDetails";
    }

    // ==================================================== Ticketing Officer ====================================================
    @RequestMapping(value = "/officer/ViewEvents", method = RequestMethod.GET)
    public String ticOffViewEvents(Principal principal) {
        
        try {
            Authentication authentication = (Authentication) principal;
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            Role r = roleRepository.findByName(RoleEnum.OFFICER).get();
           

            if (user.getUser().getRole().getName() ==r.getName()){
                User u = userRepository.findById(user.getUser().getId()).get();
                if (!u.getHasPasswordChange()){
                    //Go to change password page
                    return "ticOffChangePassword";
                }
            }
           
            // model.addAttribute("eventName", "LOGGED IN");
        }catch(Exception e){

            // model.addAttribute("eventName", "NOT LOGGED IN");
        }
        return "ticOffViewEvents";
    }

    @RequestMapping(value = "/officer/VerifyTickets", method = RequestMethod.GET)
    public String ticOffVerifyTickets(Principal principal) {
        
        try {
            Authentication authentication = (Authentication) principal;
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            Role r = roleRepository.findByName(RoleEnum.OFFICER).get();
           

            if (user.getUser().getRole().getName() ==r.getName()){
                User u = userRepository.findById(user.getUser().getId()).get();
                if (!u.getHasPasswordChange()){
                    //Go to change password page
                    return "ticOffChangePassword";
                }
            }
           
            // model.addAttribute("eventName", "LOGGED IN");
        }catch(Exception e){

            // model.addAttribute("eventName", "NOT LOGGED IN");
        }
        // this just means render index.html from static/ area
        return "ticOffVerifyTickets";
    }

    
    @RequestMapping(value = "/officer/PurchaseTickets", method = RequestMethod.GET)
    public String ticOffPurchaseTickets(Principal principal, Model model) {
        try {
            Authentication authentication = (Authentication) principal;
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            Role r = roleRepository.findByName(RoleEnum.OFFICER).orElse(null);
        
            if (user != null && r != null && user.getUser().getRole().getName().equals(r.getName())) {
                User u = userRepository.findById(user.getUser().getId()).orElse(null);
                if (u != null && !u.getHasPasswordChange()) {
                    // Redirect to change password page
                    return "redirect:/officer/changePassword";
                }
            }
        } catch (Exception e) {
            // Handle exceptions
        }
        
        // Render the purchase tickets page
        return "ticOffPurchaseTickets";
    }

    @RequestMapping(value = "/officer/changePassword", method = RequestMethod.GET)
    public String ticOffChangePassword() {
        return "ticOffChangePassword";
    }

    // ==================================================== Event Manager ====================================================
    @RequestMapping(value = "/manager/ViewEvents", method = RequestMethod.GET)
    public String eventManViewEvents(Principal principal) {
        

        return "eventManViewEvents";
    }

    
    @RequestMapping(value = "/manager/CreateEvent", method = RequestMethod.GET)
    public String eventManCreateEvent(Principal principal) {
        

        return "eventManCreateEvent";
    }

    

    @RequestMapping(value = "/manager/EditEvent/{eventId}", method = RequestMethod.GET)
    public String eventManEditEvent(@PathVariable("eventId") int eventId, Model model, Principal principal) {
       
        return "eventManEditEvent";
    }


    @RequestMapping(value = "/manager/ViewTicOfficer", method = RequestMethod.GET)
    public String eventManViewTicOfficer(Principal principal) {
        

        return "eventManViewTicOfficer";
    }

    

    @RequestMapping(value = "/manager/ViewDashboard", method = RequestMethod.GET)
    public String eventManViewDashboard(Principal principal) {
        
        
        return "eventManViewDashboard";
    }

    
}
