package com.manoj.springboot;

import com.manoj.springboot.enums.RoleEnum;
import com.manoj.springboot.model.Member;
import com.manoj.springboot.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SpringbootLmsApplication {
	@Autowired
	MemberRepository memberRepository;
	public static void main(String[] args) {
		SpringApplication.run(SpringbootLmsApplication.class, args);
	}
	@PostConstruct
	public void init(){
		// ### Creation of admin at init ###
		if(!memberRepository.existsByEmail("basnetm02@gmail.com")){
			Member member= Member.builder()
					.name("Manoj Basnet")
					.email("basnetm02@gmail.com")
					.dob(null)
					.address("Kalopul,Kathmandu")
					.gender('m')
					.phone("9843242332")
					.role(RoleEnum.LIBRARIAN)
					.password(BCrypt.hashpw("pass",BCrypt.gensalt()))
					.enabled(true)
					.build();
			memberRepository.save(member);
		}
	}
}
