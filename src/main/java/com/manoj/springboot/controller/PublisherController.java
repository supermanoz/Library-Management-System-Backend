package com.manoj.springboot.controller;

import com.manoj.springboot.exception.DoesNotExistException;
import com.manoj.springboot.model.Publisher;
import com.manoj.springboot.response.MyResponse;
import com.manoj.springboot.serviceImpl.PublisherServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    Logger logger= LoggerFactory.getLogger(PublisherController.class);
    @Autowired
    private PublisherServiceImpl publisherService;
    @GetMapping("/fetchAll")
    public List<Publisher> getAllPublishers(){
        return publisherService.getAllPublishers();
    }

    @GetMapping("/fetch/{id}")
    public Publisher getPublisher(@PathVariable long id){
        return publisherService.getPublisher(id);
    }

    @PostMapping("/save")
    public ResponseEntity<?> savePublisher(@RequestBody Publisher publisher){
        Publisher savedPublisher=publisherService.savePublisher(publisher);
        if(savedPublisher!=null){
            return new ResponseEntity<>(new MyResponse<>(HttpStatus.OK, savedPublisher),HttpStatus.OK);
        }
        return new ResponseEntity<>(new MyResponse<>(HttpStatus.OK, "Could not be saved"),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePublishers(@PathVariable long id){
        logger.trace("This is publisher id baby "+id);
        if(!publisherService.deletePublisher(id)){
            throw new DoesNotExistException("The publisher by this id doesn't exist!");
        }
        MyResponse<String> myResponse = new MyResponse<>(HttpStatus.OK, "Deleted Publisher of id: " + id);
        return new ResponseEntity<>(myResponse, HttpStatus.OK);
    }
}
