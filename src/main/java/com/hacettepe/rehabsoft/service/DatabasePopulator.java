package com.hacettepe.rehabsoft.service;

import com.hacettepe.rehabsoft.entity.Role;
import com.hacettepe.rehabsoft.repository.RoleRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DatabasePopulator {
    private final RoleRepository roleRepository;

    DatabasePopulator(RoleRepository roleRepository){
        this.roleRepository = roleRepository;

        insertRole();
    }

    public void insertRole(){
        if(roleRepository.findAll().isEmpty()){
            roleRepository.save(new Role("USER", new HashSet<>()));
            roleRepository.save(new Role("ADMIN", new HashSet<>()));
            roleRepository.save(new Role("DOCTOR", new HashSet<>()));
        }
    }
}
