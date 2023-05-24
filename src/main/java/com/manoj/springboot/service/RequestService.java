package com.manoj.springboot.service;

import com.manoj.springboot.model.Book;
import com.manoj.springboot.model.Member;
import com.manoj.springboot.model.Request;

import java.util.List;

public interface RequestService {

    public Request addRequest(Member member, Book book);
    public List<Request> fetchAll();
}
