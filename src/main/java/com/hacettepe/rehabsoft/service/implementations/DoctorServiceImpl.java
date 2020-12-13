package com.hacettepe.rehabsoft.service.implementations;

import com.hacettepe.rehabsoft.dto.DoctorDto;
import com.hacettepe.rehabsoft.entity.Doctor;
import com.hacettepe.rehabsoft.entity.Role;
import com.hacettepe.rehabsoft.entity.User;
import com.hacettepe.rehabsoft.repository.DoctorRepository;
import com.hacettepe.rehabsoft.repository.RoleRepository;
import com.hacettepe.rehabsoft.repository.UserRepository;
import com.hacettepe.rehabsoft.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public String save(String username) {
        log.warn("Doctor Servisi - Save metodu başladı");

        Doctor doctor = new Doctor();
        User user = userRepository.findByUsername(username);
        //username bulunmuyorsa hata ver
        if(user==null){
            return "Böyle bir kullanıcı bulunmuyor.Önce sisteme kayıt olup ardından atama islemini gerçekleştirin";
        }

        //Mevcut user'ın rolünü patient'tan DOCTOR'a çeviriyoruz.Artık yetkilere sahip
        final Role role = roleRepository.findByName("DOCTOR");
        user.setRole(role);

        //doctor kayıt işlemi
        doctor.setUser(userRepository.findByUsername(username));
        doctor.setPatientCollection(null);
        doctor.setVideoRequestCollection(null);
        doctorRepository.save(doctor);

        return "Doctor oluşturma işlemi başarı ile tamamlandı";
    }
}
