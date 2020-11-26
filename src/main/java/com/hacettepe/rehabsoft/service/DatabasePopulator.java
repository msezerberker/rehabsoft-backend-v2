package com.hacettepe.rehabsoft.service;

import com.hacettepe.rehabsoft.entity.*;
import com.hacettepe.rehabsoft.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;

@Service
public class DatabasePopulator {
    private final RoleRepository roleRepository;
    private final IlRepository ilRepository;
    private final IlceRepository ilceRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final ExerciseRepository exerciseRepository;

    DatabasePopulator(RoleRepository roleRepository, IlRepository ilRepository,
                      IlceRepository ilceRepository, UserRepository userRepository,
                      DoctorRepository doctorRepository, ExerciseRepository exerciseRepository){
        this.roleRepository = roleRepository;
        this.ilRepository = ilRepository;
        this.ilceRepository = ilceRepository;
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.exerciseRepository = exerciseRepository;

        insertRoleAndUser();
        insertIlAndIlce();
    }

    public void insertRoleAndUser(){
        if(roleRepository.findAll().isEmpty()){
            final Role userRole = roleRepository.save(new Role("USER", new HashSet<>()));
            final Role adminRole = roleRepository.save(new Role("ADMIN", new HashSet<>()));
            final Role doctorRole = roleRepository.save(new Role("DOCTOR", new HashSet<>()));

            // create a doctor
            if(userRepository.findAll().isEmpty()){
                User doctorUser = new User();
                doctorUser.setEmail("oktay@oktay.com");
                doctorUser.setFirstName("Oktay");
                doctorUser.setSurname("UGURLU");

                // password = admin
                doctorUser.setUsername("oktayugurlu");
                doctorUser.setPassword("$2a$10$2ykKjQXoQYuTALCkVaAVtuSkKdPdAmu1lHVEG06TRWOG0Ol8ENDHC");
                doctorUser.setRole(doctorRole);
                User userFetched = userRepository.save(doctorUser);

                Doctor doctor = new Doctor();
                doctor.setUser(userFetched);
                Doctor doctorSaved = doctorRepository.save(doctor);

                if(exerciseRepository.findAll().isEmpty()){
                    final Exercise exercise1 = exerciseRepository.save(new Exercise("Deneme exercise", "Deneme contentcontentcontent content", new ArrayList<>(), new ArrayList<>(), userFetched ));
                    final Exercise exercise2 = exerciseRepository.save(new Exercise("Deneme exercise2", "Deneme contentcontentcontent content", new ArrayList<>(), new ArrayList<>(), userFetched ));
                    final Exercise exercise3 = exerciseRepository.save(new Exercise("Deneme exercise3", "Deneme contentcontentcontent content", new ArrayList<>(), new ArrayList<>(), userFetched ));
                    final Exercise exercise4 = exerciseRepository.save(new Exercise("Deneme exercise4", "Deneme contentcontentcontent content", new ArrayList<>(), new ArrayList<>(), userFetched ));
                    final Exercise exercise5 = exerciseRepository.save(new Exercise("Deneme exercise5", "Deneme contentcontentcontent content", new ArrayList<>(), new ArrayList<>(), userFetched ));
                    final Exercise exercise6 = exerciseRepository.save(new Exercise("Deneme exercise6", "Deneme contentcontentcontent content", new ArrayList<>(), new ArrayList<>(), userFetched ));
                    final Exercise exercise7 = exerciseRepository.save(new Exercise("Deneme exercise7", "Deneme contentcontentcontent content", new ArrayList<>(), new ArrayList<>(), userFetched ));
                    final Exercise exercise8 = exerciseRepository.save(new Exercise("Deneme exercise8", "Deneme contentcontentcontent content", new ArrayList<>(), new ArrayList<>(), userFetched ));
                    final Exercise exercise9 = exerciseRepository.save(new Exercise("Deneme exercise9", "Deneme contentcontentcontent content", new ArrayList<>(), new ArrayList<>(), userFetched ));
                    final Exercise exercise10 = exerciseRepository.save(new Exercise("Deneme exercise10", "Deneme contentcontentcontent content", new ArrayList<>(), new ArrayList<>(), userFetched ));
                    final Exercise exercise11 = exerciseRepository.save(new Exercise("Deneme exercise11", "Deneme contentcontentcontent content", new ArrayList<>(), new ArrayList<>(), userFetched ));
                }
            }
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
