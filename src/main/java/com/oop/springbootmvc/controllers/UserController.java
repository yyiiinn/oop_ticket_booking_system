package com.oop.springbootmvc.controllers;

import com.oop.springbootmvc.model.TestObj;
import com.oop.springbootmvc.model.User;
import com.oop.springbootmvc.repository.TestObjRepository;
import com.oop.springbootmvc.repository.UserRepository;
import com.oop.springbootmvc.viewmodel.LoginViewModel;
import com.oop.springbootmvc.viewmodel.RegisterViewModel;
import com.oop.springbootmvc.viewmodel.UserViewModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    User user = new User(registerViewModel.getName(),"User", registerViewModel.getUsername(), encodedPassword, true, 1000);
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

//
//  @PostMapping("/api/register")
//  public TestObj add(@RequestBody User user) {
//    return this.testObjRepository.save(testObj);
//  }


}
