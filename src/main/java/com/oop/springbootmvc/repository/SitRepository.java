package com.oop.springbootmvc.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import com.oop.springbootmvc.model.Sit;
import com.oop.springbootmvc.model.Transaction;


public interface SitRepository extends JpaRepository<Sit, Long>{
       List<Sit> findByEventId(long eventId);
 
    
} 
