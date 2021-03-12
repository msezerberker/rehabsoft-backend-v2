package com.hacettepe.rehabsoft.service.implementations;

import com.hacettepe.rehabsoft.dto.RegistrationRequest;
import com.hacettepe.rehabsoft.dto.UserDto;
import com.hacettepe.rehabsoft.entity.*;
import com.hacettepe.rehabsoft.repository.RoleRepository;
import com.hacettepe.rehabsoft.repository.UserRepository;
import com.hacettepe.rehabsoft.service.NotificationService;
import com.hacettepe.rehabsoft.service.UserService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Slf4j
@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private NotificationService notificationService;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username.toLowerCase());
        if(user == null){
            throw new UsernameNotFoundException("Invalid username");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }


    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        Role role = user.getRole();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        return authorities;
    }



    @Override
    public UserDto getByUsername(String username) {
        User tempUser = userRepository.findByUsername(username);
        return modelMapper.map(tempUser, UserDto.class);
    }



    public List<UserDto> getAll() {
        List<User> data = userRepository.findAll();
        return Arrays.asList(modelMapper.map(data, UserDto[].class));
    }


    @Override
    public String updateUser(UserDto tempUser){
        log.warn("user update is on");
        User dbUser = userRepository.getOne(tempUser.getId());

        if(!dbUser.getEmail().equals(tempUser.getEmail()) ){ //email değişmişse, yeni e-mailin zaten başka biri tarafından kullanıldığını check ediyor
            if(this.isEmailExists(tempUser.getEmail())){
                return "E-mail zaten kullanılıyor.Lütfen başka bir e-mail adresi seçin";
            }
        }

        dbUser.setSurname(tempUser.getSurname());
        dbUser.setFirstName(tempUser.getFirstName());
        dbUser.setEmail(tempUser.getEmail().toLowerCase());
        userRepository.save(dbUser);
        return "Degisiklikler başarıyla kaydedildi!";

    }

    @Override
    public Boolean isUsernameExists(String username) {
        if(userRepository.findByUsername(username.toLowerCase())==null){
            return false;
        }
        return true;
    }


    @Override
    public Boolean isEmailExists(String email) {
        if(userRepository.findByEmail(email.toLowerCase())==null){
            return false;
        }
        return true;
    }


    @Override
    public Boolean deleteUser(Long id) {
       if(userRepository.existsById(id)){
           userRepository.deleteById(id);
           return true;
       }
       else{
           return false;
       }

    }




    @Override
    @Transactional
    public UserDto save(UserDto user) {
        User tempUser = modelMapper.map(user, User.class);
        tempUser = userRepository.save(tempUser);
        user.setId(tempUser.getId());
        System.out.println();
        return user;
    }



    @Transactional
    public Boolean register(RegistrationRequest registrationRequest) {
        try {
            log.warn("Register'a giriyor");
            User user = new User();
            user.setEmail(registrationRequest.getEmail().toLowerCase());
            user.setFirstName(registrationRequest.getFirstName());
            user.setSurname(registrationRequest.getSurname());
            user.setPassword(bCryptPasswordEncoder.encode(registrationRequest.getPassword()));
            user.setUsername(registrationRequest.getUsername().toLowerCase());
            final Role role = roleRepository.findByName("USER");
            //System.out.println(role.getName());
            user.setRole(role);
            User userFromDatabase = userRepository.save(user);
            notificationService.createNotificationForGeneralEvaluationForm(userFromDatabase);
            return Boolean.TRUE;

        } catch (Exception e) {
            log.error("REGISTRATION Failed=>", e);
            return Boolean.FALSE;
        }
    }

}
