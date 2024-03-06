package com.oop.springbootmvc.repository;

import com.oop.springbootmvc.model.Event;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE e.status = 'Active'")
    List<Event> findActiveEvents();
}
