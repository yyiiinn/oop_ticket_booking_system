package com.oop.springbootmvc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oop.springbootmvc.model.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long> {
    @Query("SELECT t FROM Ticket t WHERE t.guid = ?1")
    public Optional<Ticket> findByGuid(String guid);

    // Number of tickets sold per event
    @Query("SELECT COUNT(ti) FROM Ticket ti JOIN ti.transaction t JOIN t.seat s WHERE s.event.id = :eventId")
    int countByEventId(@Param("eventId") int eventId);
 
    // Customer Attendance per event
    @Query("SELECT COUNT(ti) FROM Ticket ti JOIN ti.transaction t JOIN t.seat s WHERE ti.status = :status AND s.event.id = :eventId")
    int customerAttendanceByEventID(@Param("status") String status, @Param("eventId") int eventId);

    // Number of tickets sold for all events
    @Query("SELECT COUNT(ti) FROM Ticket ti")
    int totalTicketsSold();

    // Total customer attendance for all events
    @Query("SELECT COUNT(ti) FROM Ticket ti WHERE ti.status = :status")
    int totalCustomerAttendance(@Param("status") String status);

    @Query("SELECT COUNT(ti) FROM Ticket ti JOIN ti.transaction t JOIN t.seat s WHERE ti.status = :status")
    int customerAttendanceByStatus(@Param("status") String status);

    @Query("SELECT COUNT(ti) FROM Ticket ti WHERE ti.transaction.id = :transaction_id")
    int totalTicketsSoldByTransactionId(@Param("transaction_id") long transaction_id);
}