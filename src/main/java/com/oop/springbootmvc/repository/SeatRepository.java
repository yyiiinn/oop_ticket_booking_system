package com.oop.springbootmvc.repository;

import com.oop.springbootmvc.model.Seat;

public interface SeatRepository extends CrudRepository<Seat, Long> {
    // @Query("SELECT u FROM User u WHERE u.username = ?1")
    // public Optional<User> findByUsername(String username);

}