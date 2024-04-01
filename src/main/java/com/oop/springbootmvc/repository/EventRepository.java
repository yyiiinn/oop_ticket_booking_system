package com.oop.springbootmvc.repository;

import com.oop.springbootmvc.model.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE e.status = 'Active'")
    List<Event> findActiveEvents();

    @Query("SELECT e FROM Event e ORDER BY CASE WHEN e.eventStartDate = CURRENT_DATE THEN 1 WHEN e.eventStartDate > CURRENT_DATE THEN 2 ELSE 3 END, e.eventStartDate ASC")
    List<Event> findAllEvents();

    @Query("SELECT e FROM Event e ORDER BY e.eventStartDate DESC, CASE WHEN e.eventStartDate = CURRENT_DATE THEN 1 WHEN e.eventStartDate > CURRENT_DATE THEN 2 ELSE 3 END, e.eventStartDate ASC")
    List<Event> findAllEventsDesc();


    @Query("SELECT e FROM Event e WHERE" +
       " (:name = '' OR LOWER(e.name) LIKE CONCAT('%', :name, '%'))" +
       " AND (:status = '' OR e.status = :status)" +
       " AND (:category = '' OR e.category = :category)")
    List<Event> searchEvents(@Param("name") String name, @Param("status") String status, @Param("category") String category);

    // within sales period
    @Query("SELECT e FROM Event e WHERE e.ticketSaleStartDateTime <= :currentDateTime AND e.ticketSaleEndDateTime >= :currentDateTime")
    List<Event> findBySalesStartDateTimeBeforeAndStatusNot(
        @Param("currentDateTime") LocalDateTime currentDateTime
    );

    // sales period ended
    @Query("SELECT e FROM Event e WHERE e.ticketSaleEndDateTime < :currentDateTime")
    List<Event> findAfterSalesPeriodEvent(
        @Param("currentDateTime") LocalDateTime currentDateTime
    );

    // event ongoing
    @Query("SELECT e FROM Event e WHERE e.eventStartDate = :currentDate AND e.eventStartTime <= :currentTime AND e.eventEndTime >= :currentTime")
    List<Event> findDuringEventPeriod(
        @Param("currentDate") LocalDate currentDate, 
        @Param("currentTime") LocalTime currentTime
    );

    // event ended
    @Query("SELECT e FROM Event e WHERE :currentDate >= e.eventStartDate AND e.eventEndTime >= :currentTime")
    List<Event> findEventEnded(
        @Param("currentDate") LocalDate currentDate, 
        @Param("currentTime") LocalTime currentTime
    );

}


