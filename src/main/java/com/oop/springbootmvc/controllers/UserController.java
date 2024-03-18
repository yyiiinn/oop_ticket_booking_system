package com.oop.springbootmvc.controllers;

import com.oop.springbootmvc.entities.RoleEnum;
import com.oop.springbootmvc.model.Role;
import com.oop.springbootmvc.model.User;
import com.oop.springbootmvc.repository.RoleRepository;
import com.oop.springbootmvc.repository.UserRepository;
import com.oop.springbootmvc.viewmodel.RegisterViewModel;
import com.oop.springbootmvc.viewmodel.UserViewModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  public UserController(UserRepository userRepository, RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  @PostMapping("/api/register")
  public ResponseEntity<Object> register(@RequestBody RegisterViewModel registerViewModel) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String encodedPassword = passwordEncoder.encode(registerViewModel.getPassword());
    Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);
    User user = new User(registerViewModel.getName(), registerViewModel.getUsername(), encodedPassword, true, 1000, optionalRole.get());
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

  @PostMapping("/api/addTicketingOfficer")
  public ResponseEntity<Object> addTicketingOfficer(@RequestBody RegisterViewModel registerViewModel) {
    Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.OFFICER);
    User user = new User(registerViewModel.getName(), "tckt_officer_1", "password", true, 1000, optionalRole.get());
    try {
      User registeredUser = this.userRepository.save(user);
      return new ResponseEntity<>(new UserViewModel(registeredUser.getUsername(), registeredUser.getName()), HttpStatus.OK) ;
    }catch(Exception e){
      // TODO Read the exception and throw to user registration fail
      if (e.getMessage().contains("duplicate key value violates unique constraint \"users_username_key\"")){
        return new ResponseEntity<>("Username has already been taken", HttpStatus.CONFLICT) ;
      }
      return new ResponseEntity<>("Failed to add new ticketing officer", HttpStatus.NOT_ACCEPTABLE) ;

    }
  }
//
//  @PostMapping("/api/register")
//  public TestObj add(@RequestBody User user) {
//    return this.testObjRepository.save(testObj);
//  }


}
