package com.manoj.springboot.depresecurity;//package com.manoj.springboot.depresecurity;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.sql.DataSource;
//
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter{
//
//    @Autowired
//    DataSource dataSource;
//
//    @Autowired
//    UserDetailsService userDetailsService;
//
//    @Autowired
//    JwtFilter jwtFilter;
//
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//////        auth.inMemoryAuthentication().withUser("manoj").password("manoj").roles("LIBRARIAN");
//////        auth.jdbcAuthentication()
//////                .dataSource(dataSource)
//////                .usersByUsernameQuery("SELECT username, password, enabled FROM member WHERE username = ?")
//////                .authoritiesByUsernameQuery("SELECT username, role FROM member WHERE username = ?");
////        auth.userDetailsService(userDetailsService);
////    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception{
//        return super.authenticationManagerBean();
//    }
//
//    @Bean
//    public PasswordEncoder getPasswordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }
//
//
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .cors().and().csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/authenticate").permitAll()
////                .antMatchers("/members/*").hasRole("LIBRARIAN")
////                .antMatchers("/borrows/*").hasRole("LIBRARIAN")
////                .antMatchers("/books/*").hasAnyRole("LIBRARIAN","STUDENT")
////                .and().formLogin();
//                .anyRequest().authenticated()
//                .and().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().httpBasic();
//        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
//}
