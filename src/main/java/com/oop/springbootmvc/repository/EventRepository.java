package com.oop.springbootmvc.repository;

import com.oop.springbootmvc.model.Event;

public interface EventRepository extends CrudRepository<Event, Long> {
    // @Query("SELECT u FROM User u WHERE u.username = ?1")
    // public Optional<User> findByUsername(String username);

}