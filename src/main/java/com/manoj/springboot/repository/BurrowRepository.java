package com.manoj.springboot.repository;

import com.manoj.springboot.model.Book;
import com.manoj.springboot.model.Burrow;
import com.manoj.springboot.model.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BurrowRepository extends CrudRepository<Burrow,Long> {
    public Burrow findByBurrowId(Long burrowId);
    public List<Burrow> findAllByMember(Member member);
    public boolean existsByMemberAndBookAndIsReturned(Member member, Book book,Boolean isReturned);
    public List<Burrow> findAllByIsReturned(Boolean isReturned);
}
