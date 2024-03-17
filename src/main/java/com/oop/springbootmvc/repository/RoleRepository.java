package com.oop.springbootmvc.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.oop.springbootmvc.entities.RoleEnum;
import com.oop.springbootmvc.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}
