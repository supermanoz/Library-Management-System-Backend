package com.manoj.springboot.serviceImpl;

import com.manoj.springboot.model.Book;
import com.manoj.springboot.model.Member;
import com.manoj.springboot.model.Request;
import com.manoj.springboot.repository.RequestRepository;
import com.manoj.springboot.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    RequestRepository requestRepository;

    public Request addRequest(Member member, Book book){
        Request request=new Request(member,book);
        return requestRepository.save(request);
    }

    public List<Request> fetchAll(){
        List<Request> requests=new ArrayList<>();
        requestRepository.findAll().forEach(requests::add);
        return requests;
    }
}
