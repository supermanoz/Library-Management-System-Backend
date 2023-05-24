package com.manoj.springboot.security;

import com.manoj.springboot.security.MyUserDetails;
import com.manoj.springboot.exception.DoesNotExistException;
import com.manoj.springboot.model.Member;
import com.manoj.springboot.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(!memberRepository.findByEmail(username).isPresent()){
            throw new DoesNotExistException("The user does not exist!");
        }
        Member user=memberRepository.findByEmail(username).get();
        return new MyUserDetails(user);
    }
}
