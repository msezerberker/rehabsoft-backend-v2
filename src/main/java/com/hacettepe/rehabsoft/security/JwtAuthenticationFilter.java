package com.hacettepe.rehabsoft.security;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static com.hacettepe.rehabsoft.util.Constants.TOKEN_PREFIX;
import static com.hacettepe.rehabsoft.util.Constants.HEADER_STRING;

@Service
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Qualifier("userService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);
        String username = null;
        String authToken = null;
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX,"");
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("an error occured during getting username from token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("the token is expired and not valid anymore", e);
            } catch(SignatureException e){
                logger.error("Kullanici adiniz veya sifreniz yanlıs.Lütfen tekrar deneyin");
            }
        }
        else if(header == null && req.getHeader("sec-websocket-protocol") != null){
            ///******** websocket filter ********///
            authToken = req.getHeader("sec-websocket-protocol").split(",")[1];
            authToken = authToken.trim();
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("an error occured during getting username from token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("the token is expired and not valid anymore", e);
            } catch(SignatureException e){
                logger.error("Kullanici adiniz veya sifreniz yanlıs.Lütfen tekrar deneyin");
            }
        } else {
            ///******** cookie filter ********///
            System.out.println(req.getServletPath());
//            if(!req.getServletPath().equals("/api/token") || !req.getServletPath().startsWith("/video")){
//                authToken = getFromCookie(req);
//            }
            logger.warn("couldn't find bearer string, will ignore the header, "+authToken);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthentication(authToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);
                //UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(req, res);
    }

    public String getFromCookie( HttpServletRequest request){
        String token = "";

        // get token from a Cookie
        Cookie[] cookies = request.getCookies();

        if( cookies == null || cookies.length < 1 ) {
            throw new AuthenticationServiceException( "Invalid Token" );
        }

        Cookie sessionCookie = null;
        for( Cookie cookie : cookies ) {
            System.out.println("sessionCookie: "+cookie.getName());
            if( ( "someSessionId" ).equals( cookie.getName() ) ) {
                sessionCookie = cookie;
                break;
            }
        }

        // TODO: move the cookie validation into a private method
        if( sessionCookie == null || StringUtils.isEmpty( sessionCookie.getValue() ) ) {
            throw new AuthenticationServiceException( "Invalid Token" );
        }

        return sessionCookie.getValue();
    }

}
