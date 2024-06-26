package com.oop.springbootmvc.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oop.springbootmvc.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    // Revenue generated per event
    @Query("SELECT SUM(t.cost) FROM Transaction t JOIN t.seat s WHERE t.status = :status AND s.event.id = :eventId")
    int revenueGeneratedByEventID(@Param("status") String status, @Param("eventId") int eventId);

    // Total revenue generated for all events
    @Query("SELECT SUM(t.cost) FROM Transaction t WHERE t.status = :status")
    int totalRevenueGenerated(@Param("status") String status);

    @Query("SELECT COUNT(t) FROM Transaction t")
    int countTotalTransactions();

    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.status = :status")
    int countTransactionByStatus(@Param("status") String status);

    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.status = :status and t.seat.id = :seat_id")
    int countTransactionByStatusAndSeatId(@Param("status") String status, @Param("seat_id") int seat_id);

    @Query("SELECT t FROM Transaction t")
    public Iterable<Transaction> findAll();
}