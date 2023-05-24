package com.manoj.springboot.serviceImpl;

import com.manoj.springboot.model.Book;
import com.manoj.springboot.exception.DoesNotExistException;
import com.manoj.springboot.exception.EmptyFieldException;
import com.manoj.springboot.configuration.Message;
import com.manoj.springboot.dto.BookDto;
import com.manoj.springboot.repository.BookRepository;
import com.manoj.springboot.repository.PublisherRepository;
import com.manoj.springboot.service.BookService;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    Logger logger= LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private Message message;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PublisherRepository publisherRepository;

    @Override
    public List<Book> getBooks(){
        return bookRepository.findAllByIsDeleted(false);
    }

    @Autowired
    EntityManager entityManager;

    @Autowired
    Session session;
    @Override
    public Book getBook(String isbn){
        return bookRepository.findByIsbnAndIsDeleted(isbn,false);
    }

    @Override
    public Book addBook(BookDto bookDto){
        if(!publisherRepository.existsById(bookDto.getPublisherId())){
            throw new DoesNotExistException("Publisher "+message.getDoesNotExist());
        }
        if(bookDto.getIsbn()==null){
            throw new EmptyFieldException(message.getEmptyField());
        }
        else{
            if(bookRepository.existsById(bookDto.getIsbn()) || bookRepository.existsByTitle(bookDto.getTitle())){
                Book book=new Book(bookDto.getIsbn(),bookDto.getTitle(),bookDto.getAuthor(),bookDto.getPublicationDate(), bookDto.getGenre(),bookDto.getQuantity(),bookDto.getCheckoutDuration(),bookDto.getPublisherId());
                book.setQuantity(book.getQuantity()+bookDto.getQuantity());
                return bookRepository.save(book);
            }
            Book book=new Book(bookDto.getIsbn(),bookDto.getTitle(),bookDto.getAuthor(),bookDto.getPublicationDate(), bookDto.getGenre(),bookDto.getQuantity(),bookDto.getCheckoutDuration(),bookDto.getPublisherId());
            return bookRepository.save(book);
        }
    }

    @Override
    public Boolean deleteBook(String id){
//        Book book=bookRepository.findByIsbnAndIsDeleted(id,false);
//        if(book==null){
//            return false;
//        }
//        book.setIsDeleted(true);
//        bookRepository.save(book);
//        return true;
        if(bookRepository.existsById(id)){
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Book getMostPopularBook(){
        return bookRepository.getMostPopularBook();
    }

    @Override
    public List<Book> getAllBooks() {
        Query query=session.getNamedNativeQuery("selectAll");
        List<Book> receivedBooks=query.getResultList();
        return receivedBooks;
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        List<Book> receivedBooks= bookRepository.getAllBooksByAuthor(author);
        List<Book> books=new ArrayList<>();
        receivedBooks.stream().forEach(book->{
            book.setPublisher(publisherRepository.findByPublisherId(book.getPublisher().getPublisherId()));
            books.add(book);
        });
        return books;
    }
}
