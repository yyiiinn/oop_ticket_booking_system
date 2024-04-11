package com.oop.springbootmvc.bootstrap;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.context.*;
import org.springframework.context.event.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.*;

import com.oop.springbootmvc.entities.RoleEnum;
import com.oop.springbootmvc.model.Role;
import com.oop.springbootmvc.model.User;
import com.oop.springbootmvc.repository.RoleRepository;
import com.oop.springbootmvc.repository.UserRepository;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleSeeder(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
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
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Optional<User> user = userRepository.findByUsername("manager");
        user.ifPresentOrElse(System.out::println, () -> {
            Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.MANAGER);
            String encodedPassword = passwordEncoder.encode("manager");
            User newUser = new User("manager", "manager", encodedPassword, false, 0, optionalRole.get());
            userRepository.save(newUser);
        });
        user = userRepository.findByUsername("officer");
        user.ifPresentOrElse(System.out::println, () -> {
            Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.OFFICER);
            String encodedPassword = passwordEncoder.encode("officer");
            User newUser = new User("officer", "officer", encodedPassword, false, 0, optionalRole.get());
            userRepository.save(newUser);
        });
        user = userRepository.findByUsername("user");
        user.ifPresentOrElse(System.out::println, () -> {
            Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);
            String encodedPassword = passwordEncoder.encode("user");
            User newUser = new User("user@dispostable.com", "user", encodedPassword, false, 1000, optionalRole.get());
            userRepository.save(newUser);
        });
    }
}