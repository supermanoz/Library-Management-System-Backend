package com.manoj.springboot.security;

import com.manoj.springboot.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig{

    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/members/**").hasRole(RoleEnum.LIBRARIAN.toString())
                .antMatchers("/publishers/**").hasRole(RoleEnum.LIBRARIAN.toString())
                .antMatchers("/books/**").hasAnyRole(RoleEnum.LIBRARIAN.toString(),RoleEnum.STUDENT.toString())
                .antMatchers("/burrows/**").hasAnyRole(RoleEnum.LIBRARIAN.toString(),RoleEnum.STUDENT.toString())
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().httpBasic()
                .and().authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
