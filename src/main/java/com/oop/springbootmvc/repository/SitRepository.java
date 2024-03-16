package com.oop.springbootmvc.repository;

import com.oop.springbootmvc.model.Sit;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SitRepository extends CrudRepository<Sit, Long> {
    @Query("SELECT s FROM Sit s WHERE s.event_id = :event_id")
    List<Sit> findSitByEventId(@Param("event_id") int event_id);

    @Modifying
    @Transactional
    @Query("delete from Sit s WHERE s.event_id = :event_id")
    int deleteSitByEventId(@Param("event_id") int event_id);
}


