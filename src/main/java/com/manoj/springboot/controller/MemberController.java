package com.manoj.springboot.controller;

import com.manoj.springboot.dto.MemberRequestDto;
import com.manoj.springboot.response.MyResponse;
import com.manoj.springboot.serviceImpl.MemberServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberServiceImpl memberService;

    Logger logger= LoggerFactory.getLogger(MemberController.class);

    @GetMapping("/fetchAll")
    public ResponseEntity<?> getAllMembers(){
        logger.trace("This is member controller that gets all members baby!");
        return new ResponseEntity<>(new MyResponse<>(memberService.getAllMembers()),HttpStatus.OK);
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity getMembers(@PathVariable int id,@RequestHeader("Accept-Language") String language){
        return ResponseEntity.ok().body(new MyResponse<>(memberService.getMember(id,language)));
    }

    @PostMapping("/save")
    public ResponseEntity<?> addMember(@RequestBody MemberRequestDto memberRequestDto){
        return new ResponseEntity<>(new MyResponse<>(HttpStatus.OK,memberService.addMember(memberRequestDto)), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable int id) {
        if(memberService.deleteMember(id))
         return new ResponseEntity<>(new MyResponse<>(HttpStatus.OK,"Deleted member : "+id),HttpStatus.OK);
        return new ResponseEntity<>(new MyResponse<>(HttpStatus.OK,"Sorry! Cannot delete member : "+id),HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("id") Integer memberId) throws IOException{
        return new ResponseEntity<>(new MyResponse<>(HttpStatus.OK,memberService.uploadImage(file,memberId)),HttpStatus.OK);
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadImage(@RequestParam Integer id) throws IOException {
        byte[] imageBytes= memberService.downloadImage(id);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/jpg")).body(imageBytes);
    }
}
