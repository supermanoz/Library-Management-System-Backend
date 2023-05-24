package com.manoj.springboot.serviceImpl;

import com.manoj.springboot.configuration.MyResourceBundleMessageSource;
import com.manoj.springboot.model.Book;
import com.manoj.springboot.model.Burrow;
import com.manoj.springboot.model.Member;
import com.manoj.springboot.exception.AlreadyExistsException;
import com.manoj.springboot.exception.DoesNotExistException;
import com.manoj.springboot.repository.BookRepository;
import com.manoj.springboot.repository.BurrowRepository;
import com.manoj.springboot.repository.MemberRepository;
import com.manoj.springboot.service.BurrowService;
import com.manoj.springboot.service.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@PropertySource("classpath:library.properties")
@Service
public class BurrowServiceImpl implements BurrowService {

    Logger logger= LoggerFactory.getLogger(BurrowServiceImpl.class);
    @Value("${book.maxBurrowCount}")
    private int maxBurrowCount;
    @Autowired
    private BurrowRepository burrowRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MyResourceBundleMessageSource messageSource;
    @Autowired
    private RequestService requestService;

    public Burrow addBurrow(Integer memberId, String isbn){
        Member member =memberRepository.findByMemberId(memberId);
        Book book =bookRepository.findByIsbnAndIsDeleted(isbn,false);
        if(!bookRepository.existsByIsbnAndIsDeleted(isbn,false)){
            throw new DoesNotExistException("Bro? The book does not exist!");
        }
        if(burrowRepository.existsByMemberAndBookAndIsReturned(member,book,false)){
            throw new AlreadyExistsException("Bro? You wanna burrow the same book twice? WTF man.");
        }
        Member memberData=memberRepository.findByMemberId(memberId);
        if(burrowRepository.findAllByMember(memberData).size()>5){
            throw new AlreadyExistsException("Bro? You cannot burrow more than "+this.maxBurrowCount+" books!");
        }
        if(bookRepository.findByIsbn(isbn).getQuantity()<=0){
            requestService.addRequest(member,book);
            throw new DoesNotExistException("Bro? The book is out of stock!");
        }
        Book tempBook=bookRepository.findByIsbn(isbn);
        book.setPublisher(tempBook.getPublisher());
        tempBook.setQuantity(tempBook.getQuantity()-1);
        bookRepository.save(tempBook);
        Burrow burrow =new Burrow(member,book);
        return burrowRepository.save(burrow);
    }

    public Burrow returnBook(Long burrowId){
        Burrow burrow=burrowRepository.findByBurrowId(burrowId);
        if(burrow==null){
            throw new DoesNotExistException(messageSource.messageSource().getMessage("doesNotExist",null,new Locale("np")));
        }
        if(burrow.getIsReturned()){
            throw new AlreadyExistsException("Bro you don't even have the book to return it!");
        }
        burrow.setIsReturned(true);
        Book book=bookRepository.findByIsbn(burrow.getBook().getIsbn());
        book.setQuantity(book.getQuantity()+1);
        bookRepository.save(book);
        burrow.setReturnedDate(new Timestamp(System.currentTimeMillis()));
        return burrowRepository.save(burrow);
    }

    public List<Burrow> getAllBurrows(){
        List<Burrow> burrows=new ArrayList<>();
        burrowRepository.findAll().forEach(burrow->burrows.add(calculateFine(burrow)));
        return burrows;
    }

    public List<Burrow> getBurrowByMemberId(Integer memberId){
        List<Burrow> burrows=new ArrayList<>();
        burrowRepository.findAllByMember(memberRepository.findByMemberId(memberId)).forEach(burrow ->
                burrows.add(calculateFine(burrow)));
        return burrows;
    }

    public Burrow getBurrow(Long burrowId){
        return calculateFine(burrowRepository.findByBurrowId(burrowId));
    }

    public List<Burrow> getNotReturned(){
//        return burrowRepository.findAllByIsReturned(false);
        List<Burrow> burrows=new ArrayList<>();
        burrowRepository.findAllByIsReturned(false).forEach(burrow->
            burrows.add(calculateFine(burrow)));
        return burrows;
    }

    @Override
    public void clearFine(Long burrowId){
        if(!burrowRepository.existsById(burrowId)){
            throw new DoesNotExistException("Burrow"+messageSource.messageSource().getMessage("doesNotExist",null,new Locale("en")));
        }
        Burrow burrow=burrowRepository.findByBurrowId(burrowId);
        burrow.setFineDue(false);
        burrowRepository.save(calculateFine(burrow));
    }

    public Burrow calculateFine(Burrow burrow){
        Date today=new Date();
        Long diff=TimeUnit.DAYS.toDays(today.getTime()-burrow.getBurrowDate().getTime()+burrow.getBook().getCheckoutDuration());
        if(diff>0){
            Double fine=(double)diff*7;
            burrow.setFineAmount(fine);
            burrow.setFineDue(true);
        }
        return burrow;
    }
}
