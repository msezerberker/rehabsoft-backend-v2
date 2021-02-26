package com.hacettepe.rehabsoft.service.implementations;


import com.hacettepe.rehabsoft.dto.*;
import com.hacettepe.rehabsoft.entity.Patient;
import com.hacettepe.rehabsoft.entity.Role;
import com.hacettepe.rehabsoft.entity.User;
import com.hacettepe.rehabsoft.repository.AdminRepository;
import com.hacettepe.rehabsoft.repository.DoctorRepository;
import com.hacettepe.rehabsoft.repository.RoleRepository;
import com.hacettepe.rehabsoft.repository.UserRepository;
import com.hacettepe.rehabsoft.service.AdminCrudService;
import com.hacettepe.rehabsoft.service.AdminService;
import com.hacettepe.rehabsoft.service.DoctorService;
import com.hacettepe.rehabsoft.service.UserService;
import com.hacettepe.rehabsoft.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;


@Service
@Slf4j
@RequiredArgsConstructor
public class AdminCrudServiceImpl implements AdminCrudService {



    private ModelMapper modelMapper;
    private final AdminService adminService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DoctorService doctorService;
    private final DoctorRepository doctorRepository;
    private final AdminRepository adminRepository;


    @Override
    public boolean createNewDoctor(RegistrationRequest registrationRequest) {
        //önce register'a gönder.Dönen değeri ise,
        if(userService.register(registrationRequest)){ //eger register işlemi başarılı ise, doctor'a çeviriyoruz
            //sonra doctor save'e gönder
            doctorService.save(registrationRequest.getUsername());
            return true;
        }
        return false;

    }

    @Override
    public boolean deleteDoctor(Long id) {
    log.warn("Silme servisi aktif");

        if(doctorRepository.existsByUserId(id)){
            userService.deleteUser(id);
            return true;
        }

        return false;
    }

    @Override
    public List<UserCrudDto> listAllDoctors(){

        List<User> doctorUsers= userRepository.getAllByRoleName("DOCTOR");

        return Arrays.asList(modelMapper.map(doctorUsers, UserCrudDto[].class));
    }




    @Override
    public DoctorInfoDto readDoctorInfo() {
        return null;
    }




    @Override
    public boolean createNewAdmin(RegistrationRequest registrationRequest) {
        //önce register'a gönder.Dönen değeri ise,
        if(userService.register(registrationRequest)){ //eger register işlemi başarılı ise, admin'e çeviriyoruz
            adminService.save(registrationRequest.getUsername());
            return true;
        }
        return false;

    }

    @Override
    public boolean deleteAdmin(Long id) {
        log.warn("Admin silme operasyonu aktif");

        if(adminRepository.existsByUserId(id)){
            userService.deleteUser(id);
            log.warn("Admin silme operasyonu basariyla sona erdi");
            return true;
        }

        return false;

    }


    @Override
    public List<UserDto> listAllAdmins(){
        log.warn("Admin servisine giris basarili: ");
       // Role role = roleRepository.findByName("ADMIN");
        List<User> adminUsers= userRepository.getAllByRoleName("DOCTOR");


        if(adminUsers==null){
            return null;}

        List<UserDto> admindtos=  Arrays.asList(modelMapper.map(adminUsers, UserDto[].class));



        return admindtos;




    }


}
