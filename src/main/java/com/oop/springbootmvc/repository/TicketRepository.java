package com.oop.springbootmvc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.oop.springbootmvc.model.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long> {
    @Query("SELECT t FROM Ticket t WHERE t.guid = ?1")
    public Optional<Ticket> findByGuid(String guid);
}