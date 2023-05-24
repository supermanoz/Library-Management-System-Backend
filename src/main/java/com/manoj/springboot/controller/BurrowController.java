package com.manoj.springboot.controller;

import com.manoj.springboot.model.Burrow;
import com.manoj.springboot.response.MyResponse;
import com.manoj.springboot.serviceImpl.BurrowServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/burrows")
@RestController
public class BurrowController {

    @Autowired
    private BurrowServiceImpl burrowService;
    Logger logger= LoggerFactory.getLogger(BurrowController.class);
    @GetMapping("/add")
    public ResponseEntity<?> addBurrow(@RequestParam int memberId,@RequestParam String isbn){
        logger.trace("Adding a row to Burrow");
        Burrow addedBurrow=burrowService.addBurrow(memberId,isbn);
        return new ResponseEntity<>(new MyResponse<>(HttpStatus.CREATED,addedBurrow), HttpStatus.CREATED);
    }

    @GetMapping("/return/{burrowId}")
    public ResponseEntity<?> returnBook(@PathVariable Long burrowId){
        Burrow returnBurrow=burrowService.returnBook(burrowId);
        return new ResponseEntity<>(new MyResponse<>(HttpStatus.OK,returnBurrow),HttpStatus.OK);
    }

    @GetMapping("/fetchAll")
    public List<Burrow> getAllBurrows(){
        return burrowService.getAllBurrows();
    }

    @GetMapping("/fetch/{id}")
    public Burrow getBurrow(@PathVariable Long burrowId){
        return burrowService.getBurrow(burrowId);
    }

    @GetMapping("/fetch")
    public ResponseEntity<?> getAllBurrowsByMemberId(@RequestParam Integer memberId){
        List<Burrow> burrows=burrowService.getBurrowByMemberId(memberId);
        return new ResponseEntity<>(new MyResponse<>(HttpStatus.OK,burrows),HttpStatus.OK);
    }

    @GetMapping("/fetchAllNotReturned")
    public ResponseEntity<?> getAllBurrowsByIsReturned(){
        List<Burrow> burrows=burrowService.getNotReturned();
        return new ResponseEntity<>(new MyResponse<>(HttpStatus.OK,burrows),HttpStatus.OK);
    }

}
