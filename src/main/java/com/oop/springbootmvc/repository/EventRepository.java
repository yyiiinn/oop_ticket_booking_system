package com.oop.springbootmvc.repository;

import com.oop.springbootmvc.model.Event;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE e.status = 'Active'")
    List<Event> findActiveEvents();

    @Query("SELECT e FROM Event e " +
    "ORDER BY " +
    "CASE WHEN e.event_start_date = CURRENT_DATE THEN 1 " +
    "WHEN e.event_start_date > CURRENT_DATE THEN 2 " +
    "ELSE 3 END, e.event_start_date ASC")
    List<Event> findAllEvents();
}


