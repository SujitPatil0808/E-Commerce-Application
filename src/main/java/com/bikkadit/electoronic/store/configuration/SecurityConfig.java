package com.bikkadit.electoronic.store.configuration;


import com.bikkadit.electoronic.store.service.impl.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailService userDetailsService;

//    In Memory Security We are Not Using Because it Will Store HardCoded Data On Server
//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        // create Users
////     In Memory Security Using SpringBoot
//        UserDetails admin = User.builder()
//                .username("Sujit")
//                .password(passwordEncoder().encode("sujit"))
//                .roles("ADMIN")
//
//                .build();
//
//        UserDetails normal = User.builder()
//                .username("Rushi")
//                .password(passwordEncoder().encode("rushi"))
//                .roles("NORMAL")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, normal);
//    }

    // Its Use For Authenticating
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    //Customization in form based Authentication
//    Basic Authentication & Form Based Authentication Implementation
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
////
////        // Here we Customize our form Based Authentication With Help Of This
////
////        httpSecurity.authorizeRequests()
////                .anyRequest()
////                .authenticated()
////                .and().
////                 formLogin()
////                .loginPage("login.html")
////                .loginProcessingUrl("/process-url")
////                .defaultSuccessUrl("/dashboard")
////                .failureUrl("error")
////                .and()
////                .logout()
////                .logoutUrl("/logout");
//
//
//        // This Is For Basic Authentication
//        // In This we need to send username and Password in header every time
//        // for every request we need to send data in header
//
//        httpSecurity
//                .csrf()
//                .disable()
//                .cors()
//                .disable()
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//
//                .httpBasic();
//
//        return httpSecurity.build();
//    }
}