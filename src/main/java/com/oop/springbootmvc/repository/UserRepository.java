package com.oop.springbootmvc.repository;

import com.oop.springbootmvc.model.Role;
import com.oop.springbootmvc.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    public Optional<User> findByUsername(String username);
    @Query("SELECT u FROM User u WHERE u.role = ?1")
    public Iterable<User> getAllByRole(Role r);
}