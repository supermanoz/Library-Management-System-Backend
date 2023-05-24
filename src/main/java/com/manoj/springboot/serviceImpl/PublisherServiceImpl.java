package com.manoj.springboot.serviceImpl;

import com.manoj.springboot.model.Publisher;
import com.manoj.springboot.exception.DoesNotExistException;
import com.manoj.springboot.repository.PublisherRepository;
import com.manoj.springboot.response.MyResponse;
import com.manoj.springboot.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublisherServiceImpl implements PublisherService {
    @Autowired
    private PublisherRepository publisherRepository;

    @Override
    public List<Publisher> getAllPublishers(){
        List<Publisher> publishers =new ArrayList<>();
        publisherRepository.findAll().forEach(publishers::add);
        return publishers;
    }

    @Override
    public Publisher getPublisher(long id){
        return publisherRepository.findByPublisherId(id);
    }

    @Override
    public Publisher savePublisher(Publisher publisher){
        return publisherRepository.save(publisher);
    }

    @Override
    public boolean deletePublisher(long id) {
        Long publisherId=id;
        if(!publisherRepository.existsById(publisherId)){
            return false;
        }
        else {
            publisherRepository.deleteById(publisherId);
            return true;
        }
    }
}
