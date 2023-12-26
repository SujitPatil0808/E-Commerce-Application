package com.bikkadit.electoronic.store.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    //get token from request
    //validate token
    //get user name from token
    // load user from associate with token
    // set authentication


    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        // Check Header Is Available Or Not
        String requestHeader = request.getHeader("Authorization");
        log.info("Header is :{} " + requestHeader);

        String username = null;
        String token = null;
        // req header is not null And Start With Bearer
        if (requestHeader != null && requestHeader.startsWith("Bearer")) {

            token = requestHeader.substring(7);


            try {


                username = jwtTokenHelper.getUsernamefromToken(token);


            } catch (IllegalArgumentException ex) {
                log.info("IllegalArgument Exception while fetching UserName");
                ex.printStackTrace();

            } catch (ExpiredJwtException ex) {
                log.info("Given Jwt Token is Expired");
                ex.printStackTrace();

            } catch (MalformedJwtException ex) {
                log.info("Something Went Wrong With Jwt Token ");
                ex.printStackTrace();
            }


        } else {
            log.info("Invalid Header Value !!!!");
        }

            // username null nahi a ani authentication set nahi a mhnje userlogin nahi ahe
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){


            // userDetails From UserName
            UserDetails user = this.userDetailsService.loadUserByUsername(username);
            Boolean validationToken = this.jwtTokenHelper.validateToken(token, user);

            if (validationToken) {
                // We Use Authentication for userName And Password
                UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }else {
                log.info("Validation Fails !!!");
            }

        }

            filterChain.doFilter(request,response);


    }
}
