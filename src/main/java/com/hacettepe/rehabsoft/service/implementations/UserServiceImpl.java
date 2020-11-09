package com.hacettepe.rehabsoft.service.implementations;

import com.hacettepe.rehabsoft.dto.ParentDto;
import com.hacettepe.rehabsoft.dto.PhoneDto;
import com.hacettepe.rehabsoft.dto.RegistrationRequest;
import com.hacettepe.rehabsoft.dto.UserDto;
import com.hacettepe.rehabsoft.entity.*;
import com.hacettepe.rehabsoft.repository.PatientRepository;
import com.hacettepe.rehabsoft.repository.RoleRepository;
import com.hacettepe.rehabsoft.repository.UserRepository;
import com.hacettepe.rehabsoft.service.ParentService;
import com.hacettepe.rehabsoft.service.PatientService;
import com.hacettepe.rehabsoft.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
    private PatientRepository patientRepository;

    @Autowired
    private ParentService parentService;
    @Autowired
    private PatientService patientService;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }


    //DEGISTI
    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        Role role = user.getRole();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        return authorities;
        //return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }



    @Override
    public UserDto getById(Long id) {
        User tempUser = userRepository.getOne(id);
        return modelMapper.map(tempUser, UserDto.class);
    }

    public List<UserDto> getAll() {
        List<User> data = userRepository.findAll();
        return Arrays.asList(modelMapper.map(data, UserDto[].class));
    }

    @Override
    public UserDto getByUsername(String username) {
        User tempUser = userRepository.findByUsername(username);
        return modelMapper.map(tempUser, UserDto.class);
    }

    @Override
    @Transactional
    public UserDto save(UserDto user) {
        //Transforms UserDto to the User object and save it
        User tempUser = modelMapper.map(user, User.class);
        tempUser = userRepository.save(tempUser);
        user.setId(tempUser.getId());
        return user;
    }



    //DİGER KONTROLLERİ DE YAP!!! USERNAME ZATEN VAR GİBİ VS
    //Gelen registerDto'sunu veritabanına kaydediyoruz
    @Transactional
    public Boolean register(RegistrationRequest registrationRequest) {
        try {

            User isRegistered= userRepository.findByUsername(registrationRequest.getUsername());
            if(isRegistered != null){
                throw new Exception("Kullanıcı adı zaten kayıtlı!");
            }
            User user = new User();
            user.setEmail(registrationRequest.getEmail());
            user.setFirstName(registrationRequest.getFirstName());
            user.setSurname(registrationRequest.getSurname());
            //Password'u bcrypt ile sifreleyip kaydediyoruz
            user.setPassword(bCryptPasswordEncoder.encode(registrationRequest.getPassword()));
            user.setUsername(registrationRequest.getUsername());
            final Role role = roleRepository.findByName("USER");
            System.out.println(role.getName());
            user.setRole(role);
            userRepository.save(user);

            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("REGISTRATION Failed=>", e);
            return Boolean.FALSE;
        }
    }


}
