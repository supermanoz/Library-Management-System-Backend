package com.manoj.springboot.service;

import com.manoj.springboot.model.Burrow;

import java.util.List;

public interface BurrowService {
    public Burrow addBurrow(Integer memberId, String isbn);
    public Burrow returnBook(Long burrowId);
    public List<Burrow> getAllBurrows();
    public List<Burrow> getBurrowByMemberId(Integer memberId);
    public Burrow getBurrow(Long burrowId);
    public List<Burrow> getNotReturned();
    public void clearFine(Long burrowId);
}
