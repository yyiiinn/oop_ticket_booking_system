package com.oop.springbootmvc.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import com.oop.springbootmvc.model.Event;

public interface EventRepository extends JpaRepository<Event, Long>{
    
    
} 
