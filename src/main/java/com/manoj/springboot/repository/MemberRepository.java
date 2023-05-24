package com.manoj.springboot.repository;

import com.manoj.springboot.model.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member,Integer> {
    public Member findByMemberId(Integer id);
    public boolean existsByEmail(String email);
    public boolean existsByPhone(String phone);
    public Optional<Member> findByName(String name);
    public Optional<Member> findByEmail(String email);
}
