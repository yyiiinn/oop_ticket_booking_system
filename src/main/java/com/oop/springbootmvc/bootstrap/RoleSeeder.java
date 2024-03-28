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

        String encodedPassword = passwordEncoder.encode("manager");
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.MANAGER);
        User user = new User("manager", "manager", encodedPassword, false, 0, optionalRole.get());
        userRepository.save(user);

        encodedPassword = passwordEncoder.encode("officer");
        optionalRole = roleRepository.findByName(RoleEnum.OFFICER);
        user = new User("officer", "officer", encodedPassword, false, 0, optionalRole.get());
        userRepository.save(user);

        encodedPassword = passwordEncoder.encode("user");
        optionalRole = roleRepository.findByName(RoleEnum.USER);
        user = new User("user", "user", encodedPassword, false, 1000, optionalRole.get());
        userRepository.save(user);

        
    }
}