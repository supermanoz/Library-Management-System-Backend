package com.manoj.springboot.repository;

import com.manoj.springboot.model.Request;
import org.springframework.data.repository.CrudRepository;

public interface RequestRepository extends CrudRepository<Request, Long> {

}
