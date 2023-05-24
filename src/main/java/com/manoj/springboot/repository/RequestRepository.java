package com.manoj.springboot.repository;

import com.manoj.springboot.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {

}
