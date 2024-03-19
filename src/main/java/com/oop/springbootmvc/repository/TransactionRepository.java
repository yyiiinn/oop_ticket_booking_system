package com.oop.springbootmvc.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import com.oop.springbootmvc.model.Transaction;
import com.oop.springbootmvc.model.Sit;
import com.oop.springbootmvc.model.Event;
import com.oop.springbootmvc.model.User;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT t.id AS id, t.status AS status, t.customer_id AS customerId, t.sit_id AS sitId, t.purchased_date_time AS purchasedDateTime, t.cost AS cost, e.name AS eventName, e.id as eventId, e.event_start_date as eventStartDate, e.event_start_time as eventStartTime, s.number_of_sits as noSits, t.cancellation_cost as userCancellationCost, e.cancellation_cost as eventCancellationCost " +
            "FROM transactions t LEFT JOIN sits s ON s.id = t.sit_id LEFT JOIN events e ON e.id = s.event_id " +
            "WHERE t.customer_id = :customerId" ,nativeQuery = true)
    List<Object[]> findTransactionsByCustId(@Param("customerId") Long customerId);
    
    @Query(value = "SELECT ti.guid as guid, ti.status as seatNo, s.type as seatType, e.name as eventName, e.event_start_date as startDate, e.event_start_time as startTime, e.event_end_time as endTime " +
    "FROM tickets ti LEFT JOIN transactions t ON ti.transaction_id = t.id LEFT JOIN sits s ON s.id = t.sit_id LEFT JOIN events e ON s.event_id = e.id " +
    "WHERE t.id = :transactionId" ,nativeQuery = true)
    List<Object[]> findTicketsByTransactionId(@Param("transactionId") Long transactionId);

    @Query(value = "SELECT * FROM transactions ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Object[] findLatestEntry();
    

}
    

