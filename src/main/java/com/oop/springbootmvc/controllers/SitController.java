package com.oop.springbootmvc.controllers;

import org.springframework.stereotype.Controller;

import com.oop.springbootmvc.model.Transaction;
import com.oop.springbootmvc.model.User;
import com.oop.springbootmvc.repository.TransactionRepository;
import com.oop.springbootmvc.repository.UserRepository;
import com.oop.springbootmvc.viewmodel.CancellationViewModel;
import com.oop.springbootmvc.viewmodel.BookingViewModel;
import com.oop.springbootmvc.model.Event;
import com.oop.springbootmvc.repository.EventRepository;
import com.oop.springbootmvc.model.Sit;
import com.oop.springbootmvc.repository.SitRepository;



import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/sit")
public class SitController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private SitRepository sitRepository;
    @Autowired
    private EventRepository eventRepository;

    //UPDATE Sit record (add back number of seats) after
    @PutMapping("/addSits/{id}")
    public ResponseEntity<Sit> addSitCount(@PathVariable("id") long id, @RequestBody CancellationViewModel viewModel ) {
        Optional<Sit> sit = sitRepository.findById(id);
        if (sit.isPresent()) {
            Sit existingSit = sit.get();
            existingSit.setNumberOfSits(existingSit.getNumberOfSits() + viewModel.getNumberOfSits());
            sitRepository.save(existingSit);
            return ResponseEntity.ok(existingSit);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/deductSits/{id}")
    public ResponseEntity<Sit> deductSitCount(@PathVariable("id") long id, @RequestBody BookingViewModel viewModel ) {
        Optional<Sit> sit = sitRepository.findById(id);
        if (sit.isPresent()) {
            Sit existingSit = sit.get();
            existingSit.setNumberOfSits(existingSit.getNumberOfSits() - viewModel.getNoOfSits());
            sitRepository.save(existingSit);
            return ResponseEntity.ok(existingSit);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
