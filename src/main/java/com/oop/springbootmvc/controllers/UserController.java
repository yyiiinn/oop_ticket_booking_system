package com.oop.springbootmvc.controllers;

import com.oop.springbootmvc.entities.RoleEnum;
import com.oop.springbootmvc.model.CustomUserDetails;
import com.oop.springbootmvc.model.Payment;
import com.oop.springbootmvc.model.Role;
import com.oop.springbootmvc.model.User;
import com.oop.springbootmvc.repository.PaymentRepository;
import com.oop.springbootmvc.repository.RoleRepository;
import com.oop.springbootmvc.repository.UserRepository;
import com.oop.springbootmvc.viewmodel.ConfirmPaymentViewModel;
import com.oop.springbootmvc.viewmodel.PaymentViewModel;
import com.oop.springbootmvc.viewmodel.RegisterViewModel;
import com.oop.springbootmvc.viewmodel.StripeViewModel;
import com.oop.springbootmvc.viewmodel.TicketOfficerViewModel;
import com.oop.springbootmvc.viewmodel.UserViewModel;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class UserController {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PaymentRepository paymentRepository;

  public UserController(UserRepository userRepository, RoleRepository roleRepository, PaymentRepository paymentRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.paymentRepository = paymentRepository;
  }

  @PostMapping("/api/register")
  public ResponseEntity<Object> register(@RequestBody RegisterViewModel registerViewModel) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String encodedPassword = passwordEncoder.encode(registerViewModel.getPassword());
    Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);
    User user = new User(registerViewModel.getUsername(), registerViewModel.getName(), encodedPassword, true, 1000, optionalRole.get());
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


  @PostMapping("/api/Customer/topup")
  public ResponseEntity<Object> topUp(@RequestBody PaymentViewModel paymentViewModel, Principal principal) {
    try{
      Authentication authentication = (Authentication) principal;
      CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
      User tempUser = user.getUser();
      User u = userRepository.findById(tempUser.getId()).get();
      Stripe.apiKey ="sk_test_51OyoY0Il7ocdHMiC9L7GFWA9BWyBYRH7LCL2jgrErmCpwgL0WZ0xorll2eqP4CpWy4ubUkUU7ttNcWOWjoNzWXpO00mVs4L1eL";
      PaymentIntentCreateParams params =
        PaymentIntentCreateParams.builder()
          .setAmount((long) (paymentViewModel.getTopUpValue()*100))
          .setCurrency("sgd")
          .setAutomaticPaymentMethods(
            PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
              .setEnabled(true)
              .build()
          )
          .build();
      PaymentIntent paymentIntent = PaymentIntent.create(params);
      String secret = paymentIntent.getId();
      paymentRepository.save(new Payment("Created", secret, u));
      StripeViewModel vm = new StripeViewModel(paymentIntent.getClientSecret());
      return ResponseEntity.ok().body(vm);
    }catch(Exception e){
        System.out.println(e);
        return ResponseEntity.status(403).body("");
    }
  }

  @PostMapping("/api/Customer/confirmTopUp")
  public ResponseEntity<Object> confirmTopUp(@RequestBody ConfirmPaymentViewModel confirmPaymentViewModel, Principal principal) {
    try{
      Authentication authentication = (Authentication) principal;
      CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
      User tempUser = user.getUser();
      User u = userRepository.findById(tempUser.getId()).get();
      Set<Payment> payments = u.getPayments();
      for (Payment payment : payments) {
        if (payment.getSecret().equals(confirmPaymentViewModel.getPaymentIntent()) && payment.getStatus().equals("Created")){

          Stripe.apiKey ="sk_test_51OyoY0Il7ocdHMiC9L7GFWA9BWyBYRH7LCL2jgrErmCpwgL0WZ0xorll2eqP4CpWy4ubUkUU7ttNcWOWjoNzWXpO00mVs4L1eL";
          PaymentIntent paymentIntent = PaymentIntent.retrieve(confirmPaymentViewModel.getPaymentIntent());
          System.out.println(paymentIntent.getStatus());

          if(paymentIntent.getStatus().equals("succeeded")){
            payment.setStatus("Claimed");
            paymentRepository.save(payment);
            u.setBalance(u.getBalance()+(paymentIntent.getAmount()/100));
            userRepository.save(u);
            return ResponseEntity.ok().body("");
    
          }else{
            return ResponseEntity.status(401).body("");
          }
        }
      }
      return ResponseEntity.status(401).body("");
    }catch(Exception e){
        System.out.println(e);
        return ResponseEntity.status(403).body("");
    }
  }

  

}
