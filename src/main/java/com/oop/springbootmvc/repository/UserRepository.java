package com.oop.springbootmvc.repository;

import com.oop.springbootmvc.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

import java.util.Optional;
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    public Optional<User> findByUsername(String username);

    

    //test
    @Query(value = "SELECT * FROM User u WHERE u.role = 'User'", nativeQuery = true)
    public Collection<User> findByRoleNative(String role);

}