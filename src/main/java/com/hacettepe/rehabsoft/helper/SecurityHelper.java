package com.hacettepe.rehabsoft.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SecurityHelper {

    public String getUsername(){
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            username = ((User)principal).getUsername();
        } else {
            username = principal.toString();
        }
        log.warn("SecurityHelper-->" + username);
        return username;
    }
}
