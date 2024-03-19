package com.oop.springbootmvc.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.*;
import com.oop.springbootmvc.model.Transaction;
import com.oop.springbootmvc.model.Event;
import com.oop.springbootmvc.model.User;
import com.oop.springbootmvc.model.Ticket;

public interface TicketRepository extends  CrudRepository<Ticket, Long> {

    List<Ticket> findByTransactionId(Long transactionId);    

}