package com.hacettepe.rehabsoft.service.implementations;

import com.hacettepe.rehabsoft.entity.Admin;
import com.hacettepe.rehabsoft.entity.Doctor;
import com.hacettepe.rehabsoft.entity.Role;
import com.hacettepe.rehabsoft.entity.User;
import com.hacettepe.rehabsoft.repository.AdminRepository;
import com.hacettepe.rehabsoft.repository.RoleRepository;
import com.hacettepe.rehabsoft.repository.UserRepository;
import com.hacettepe.rehabsoft.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AdminRepository adminRepository;


    @Override
    public boolean save(String username) throws UsernameNotFoundException {
        log.warn("Admin Servisi - Save metodu başladı");

        Admin admin = new Admin();
        User user = userRepository.findByUsername(username);
        //username bulunmuyorsa hata ver
        if(user==null){
            return false;
        }

        final Role role = roleRepository.findByName("ADMIN");
        user.setRole(role);
        admin.setUser(user);
        adminRepository.save(admin);

        return true;
    }
}
