package com.oop.springbootmvc.repository;

import com.oop.springbootmvc.model.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE e.status = 'Active'")
    List<Event> findActiveEvents();

    @Query("SELECT e FROM Event e " +
    "ORDER BY " +
    "CASE WHEN e.eventStartDate = CURRENT_DATE THEN 1 " +
    "WHEN e.eventStartDate > CURRENT_DATE THEN 2 " +
    "ELSE 3 END, e.eventStartDate ASC")
    List<Event> findAllEvents();
}


