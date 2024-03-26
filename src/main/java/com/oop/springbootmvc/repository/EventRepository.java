package com.oop.springbootmvc.repository;

import com.oop.springbootmvc.model.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE e.status = 'Active'")
    List<Event> findActiveEvents();

    @Query("SELECT e FROM Event e ORDER BY CASE WHEN e.eventStartDate = CURRENT_DATE THEN 1 WHEN e.eventStartDate > CURRENT_DATE THEN 2 ELSE 3 END, e.eventStartDate ASC")
    List<Event> findAllEvents();


    @Query("SELECT e FROM Event e WHERE" +
       " (:name = '' OR LOWER(e.name) LIKE CONCAT('%', :name, '%'))" +
       " AND (:status = '' OR e.status = :status)" +
       " AND (:category = '' OR e.category = :category)")
    List<Event> searchEvents(@Param("name") String name, @Param("status") String status, @Param("category") String category);
}


