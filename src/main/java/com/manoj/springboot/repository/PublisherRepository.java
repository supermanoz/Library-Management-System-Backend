package com.manoj.springboot.repository;

import com.manoj.springboot.model.Publisher;
import org.springframework.data.repository.CrudRepository;

public interface PublisherRepository extends CrudRepository<Publisher,Long> {
    public Publisher findByPublisherId(long id);
    public boolean existsByName(String name);
}
