package com.oop.springbootmvc.controllers;

import com.oop.springbootmvc.entities.RoleEnum;
import com.oop.springbootmvc.model.CustomUserDetails;
import com.oop.springbootmvc.model.Event;
import com.oop.springbootmvc.model.Role;
import com.oop.springbootmvc.model.Transaction;
import com.oop.springbootmvc.model.User;
import com.oop.springbootmvc.repository.RoleRepository;
import com.oop.springbootmvc.repository.UserRepository;
import com.oop.springbootmvc.viewmodel.CustomerTransactionViewModel;
import com.oop.springbootmvc.viewmodel.EventOnlyViewModel;
import com.oop.springbootmvc.viewmodel.RegisterViewModel;
import com.oop.springbootmvc.viewmodel.TicketOfficerViewModel;
import com.oop.springbootmvc.viewmodel.UserViewModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

  @PostMapping("/api/manager/register")
  public ResponseEntity<Object> officerRegister(@RequestBody RegisterViewModel registerViewModel) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String encodedPassword = passwordEncoder.encode("password");
    Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.OFFICER);
    User user = new User(registerViewModel.getName(), registerViewModel.getUsername(), encodedPassword, false, 0, optionalRole.get());
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

  @GetMapping("/api/customer/balance")
  public ResponseEntity<Object> getCustBalance(Principal principal) {
    try{
      Authentication authentication = (Authentication) principal;
      CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
      User tempUser = user.getUser();
      User u = userRepository.findById(tempUser.getId()).get();
      float userBalance = u.getBalance();

      return ResponseEntity.ok().body(userBalance);
  }catch(Exception e){
      System.out.println(e);
      return ResponseEntity.status(403).body("");

  }
}

  @GetMapping("/api/manager/getAllOfficer")
  public ResponseEntity<Object> getAllEvents() {
    Role r = roleRepository.findByName(RoleEnum.OFFICER).get();
    List<User> users = (List<User>) userRepository.getAllByRole(r);
    ArrayList<TicketOfficerViewModel> toReturn = new ArrayList<>();
    for (User user : users) {
      toReturn.add(new TicketOfficerViewModel(user));
    }
    return ResponseEntity.ok(toReturn);
  }

  @PostMapping("/api/officer/changePassword")
  public ResponseEntity<Object> changePassword(@RequestBody RegisterViewModel registerViewModel, Principal principal) {
    try{
      Authentication authentication = (Authentication) principal;
      CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
      User tempUser = user.getUser();
      Role r = roleRepository.findByName(RoleEnum.OFFICER).get();
      User u = userRepository.findById(tempUser.getId()).get();
      if (u.getRole().getName() == r.getName()){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(registerViewModel.getPassword());
        u.setPassword(encodedPassword);
        u.setHasPasswordChange(true);
        userRepository.save(u);
        return ResponseEntity.ok().body("");
      }
      return ResponseEntity.status(403).body("");

    }catch(Exception e){
        System.out.println(e);
        return ResponseEntity.status(403).body("");
    }
  }

  
//
//  @PostMapping("/api/register")
//  public TestObj add(@RequestBody User user) {
//    return this.testObjRepository.save(testObj);
//  }


}
