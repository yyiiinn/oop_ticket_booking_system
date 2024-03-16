package com.oop.springbootmvc.controllers;

import com.oop.springbootmvc.model.Sit;
import com.oop.springbootmvc.repository.SitRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SitController {
  private final SitRepository sitRepository;

  public SitController(SitRepository sitRepository) {
    this.sitRepository = sitRepository;
  }

  @RequestMapping(value = "/api/getSitByEventId/{eventId}", method = RequestMethod.GET)
  public ResponseEntity<Object> getSitByEventId(@PathVariable("eventId") int eventId, Model model, Principal principal) {
    try {
      List<Sit> listSits = sitRepository.findSitByEventId(eventId);
      return ResponseEntity.ok().body(listSits);
    } catch (Exception e) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Unable to retrieve event sit details");
        responseBody.put("status", "400");
        return ResponseEntity.ok().body(responseBody);
    }
  }
}