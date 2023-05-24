package com.manoj.springboot.service;

import com.manoj.springboot.model.Publisher;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PublisherService {
    public List<Publisher> getAllPublishers();
    public Publisher getPublisher(long id);
    public Publisher savePublisher(Publisher publisher);
    public boolean deletePublisher(long id);
}
