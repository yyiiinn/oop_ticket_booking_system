package com.oop.springbootmvc.controllers;

import java.util.*;

import com.oop.springbootmvc.model.TestObj;
import com.oop.springbootmvc.repository.TestObjRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestObjController {
  private final TestObjRepository testObjRepository;

  public TestObjController(TestObjRepository testObjRepository) {
    this.testObjRepository = testObjRepository;
  }
  @GetMapping("/api/test")
  Iterable<TestObj> all() {
    return this.testObjRepository.findAll();
  }

  @GetMapping("/api/test/{id}")
  Optional<TestObj> one(@PathVariable("id") int id) {
    return this.testObjRepository.findById(id);
  }

  @PostMapping("/api/test")
  public TestObj add(@RequestBody TestObj testObj) {
    return this.testObjRepository.save(testObj);
  }

  @DeleteMapping("/api/test/{id}")
  public void delete(@PathVariable("id") Long id) {
    try {
      this.testObjRepository.deleteById(id);
    }catch(Exception e){
      e.printStackTrace();
    }
  }

}
