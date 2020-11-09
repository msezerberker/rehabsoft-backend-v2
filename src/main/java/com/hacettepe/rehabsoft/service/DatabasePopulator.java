package com.hacettepe.rehabsoft.service;

import com.hacettepe.rehabsoft.entity.Il;
import com.hacettepe.rehabsoft.entity.Ilce;
import com.hacettepe.rehabsoft.entity.Role;
import com.hacettepe.rehabsoft.entity.User;
import com.hacettepe.rehabsoft.repository.IlRepository;
import com.hacettepe.rehabsoft.repository.IlceRepository;
import com.hacettepe.rehabsoft.repository.RoleRepository;
import com.hacettepe.rehabsoft.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;

@Service
public class DatabasePopulator {
    private final RoleRepository roleRepository;
    private final IlRepository ilRepository;
    private final IlceRepository ilceRepository;
    private final UserRepository userRepository;

    DatabasePopulator(RoleRepository roleRepository, IlRepository ilRepository, IlceRepository ilceRepository, UserRepository userRepository){
        this.roleRepository = roleRepository;
        this.ilRepository = ilRepository;
        this.ilceRepository = ilceRepository;
        this.userRepository = userRepository;

        insertRoleAndUser();
        insertIlAndIlce();
    }

    public void insertRoleAndUser(){
        if(roleRepository.findAll().isEmpty()){
            final Role userRole = roleRepository.save(new Role("USER", new HashSet<>()));
            final Role adminRole = roleRepository.save(new Role("ADMIN", new HashSet<>()));
            final Role doctorRole = roleRepository.save(new Role("DOCTOR", new HashSet<>()));

//            if(userRepository.findAll().isEmpty()){
//                userRepository.save(new User("oktay", "password"));
//                userRepository.save(new Role("ADMIN", new HashSet<>()));
//                userRepository.save(new Role("DOCTOR", new HashSet<>()));
//            }
        }
    }

    public void insertIlAndIlce(){
        if(ilRepository.findAll().isEmpty() && ilceRepository.findAll().isEmpty()){
            Il il1 = ilRepository.save(new Il("Ankara", new ArrayList<>()));
            Il il2 = ilRepository.save(new Il("İstanbul", new ArrayList<>()));

            ilceRepository.save(new Ilce("Çankaya", il1));
            ilceRepository.save(new Ilce("Mamak", il1));

            ilceRepository.save(new Ilce("Kadıköy", il2));
            ilceRepository.save(new Ilce("Beşiktaş", il2));
        }
    }

}
