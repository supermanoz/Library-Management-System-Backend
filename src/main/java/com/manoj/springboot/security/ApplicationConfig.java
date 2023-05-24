package com.manoj.springboot.security;

import com.manoj.springboot.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

    @Autowired
    MemberRepository memberRepository;
//    @Bean
//    public UserDetailsService userDetailsService(){
////        return new UserDetailsService() {
////            @Override
////            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////                Member user=memberRepository.findByEmail(username).get();
////                if(user==null){
////                    throw new UsernameNotFoundException("No user with this email found!");
////                }
////                return new MyUserDetails(user);
////            }
////        };
//
//        return username -> {
//            Member user=memberRepository.findByEmail(username).get();
//            if(user==null){
//                throw new UsernameNotFoundException("No user with this email found!");
//            }
//            return new MyUserDetails(user);
//        };
//    }

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}


