package com.oop.springbootmvc.repository;

import com.oop.springbootmvc.model.Seat;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends CrudRepository<Seat, Long> {
    @Query("SELECT s FROM Seat s WHERE s.event = :event_id")
    List<Seat> findSeatByEventId(@Param("event_id") long event_id);

    // @Modifying
    // @Transactional
    // @Query("delete from Seat s WHERE s.event_id = :event_id")
    // int deleteSeatByEventId(@Param("event_id") long event_id);
}


