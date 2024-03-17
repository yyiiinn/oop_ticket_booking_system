package com.oop.springbootmvc.bootstrap;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.context.*;
import org.springframework.context.event.*;
import org.springframework.stereotype.*;

import com.oop.springbootmvc.entities.RoleEnum;
import com.oop.springbootmvc.model.Role;
import com.oop.springbootmvc.repository.RoleRepository;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;


    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

    private void loadRoles() {
        RoleEnum[] roleNames = new RoleEnum[] { RoleEnum.USER, RoleEnum.MANAGER, RoleEnum.OFFICER };
       

        Arrays.stream(roleNames).forEach((roleName) -> {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);

            optionalRole.ifPresentOrElse(System.out::println, () -> {
                Role roleToCreate = new Role();

                roleToCreate.setName(roleName);

                roleRepository.save(roleToCreate);
            });
        });
    }
}

