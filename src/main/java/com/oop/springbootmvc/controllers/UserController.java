package com.oop.springbootmvc.controllers;

import com.oop.springbootmvc.model.Sit;
import com.oop.springbootmvc.model.TestObj;
import com.oop.springbootmvc.model.Transaction;
import com.oop.springbootmvc.model.User;
import com.oop.springbootmvc.repository.TestObjRepository;
import com.oop.springbootmvc.repository.UserRepository;
import com.oop.springbootmvc.service.CustomUserDetailsService;
import com.oop.springbootmvc.viewmodel.LoginViewModel;
import com.oop.springbootmvc.viewmodel.RegisterViewModel;
import com.oop.springbootmvc.viewmodel.UserViewModel;
import com.oop.springbootmvc.viewmodel.CancellationViewModel;
import com.oop.springbootmvc.viewmodel.BookingViewModel;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {

 
  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostMapping("/api/register")
  public ResponseEntity<Object> register(@RequestBody RegisterViewModel registerViewModel) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String encodedPassword = passwordEncoder.encode(registerViewModel.getPassword());
    User user = new User(registerViewModel.getName(),"User", registerViewModel.getUsername(),encodedPassword, true, 1000);
    try {
      User registeredUser = this.userRepository.save(user);
      return new ResponseEntity<>(new UserViewModel(registeredUser.getUsername(), registeredUser.getName()), HttpStatus.OK) ;
    }catch(Exception e){
      // TODO Read the exception and throw to user registration fail
      if (e.getMessage().contains("duplicate key value violates unique constraint \"users_username_key\"")){
        return new ResponseEntity<>("Username has already been taken", HttpStatus.CONFLICT) ;
      }
      return new ResponseEntity<>("Failed to Register", HttpStatus.NOT_ACCEPTABLE) ;

    }
  }
  @GetMapping("/getUser/{userID}")
  public ResponseEntity<User> getUserById(@PathVariable("userID") long userId) {
      // Fetch user by the given user ID
      Optional<User> userOptional = userRepository.findById(userId);
      
      // Check if the user exists
      if (userOptional.isPresent()) {
          // Return user with a HTTP status of OK (200)
          return ResponseEntity.ok().body(userOptional.get());
      } else {
          // If user not found, return HTTP status of NOT FOUND (404)
          return ResponseEntity.notFound().build();
      }
  }
    //UPDATE User record (increase balance)
    @PutMapping("/increaseBalance/{id}")
    public ResponseEntity<User> increaseBalance(@PathVariable("id") long id, @RequestBody CancellationViewModel viewModel ) {
        Optional<User> user = this.userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setBalance(existingUser.getBalance() + viewModel.getCost() - viewModel.getCancellationCost());;
            userRepository.save(existingUser);
            return ResponseEntity.ok(existingUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }  
    //UPDATE User record (deduct balance)
    @PutMapping("/deductBalance/{id}")
    public ResponseEntity<User> deductBalance(@PathVariable("id") long id, @RequestBody BookingViewModel viewModel ) {
        Optional<User> user = this.userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setBalance(existingUser.getBalance() - viewModel.getBookingCost());;
            userRepository.save(existingUser);
            return ResponseEntity.ok(existingUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }  

  
}




