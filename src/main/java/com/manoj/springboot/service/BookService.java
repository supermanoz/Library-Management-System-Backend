package com.manoj.springboot.service;

import com.manoj.springboot.dto.BookDto;
import com.manoj.springboot.model.Book;

import java.util.List;

public interface BookService {
    public List<Book> getBooks();
    public Book getBook(String isbn);
    public Book addBook(BookDto bookDto);
    public Boolean deleteBook(String id);
    public Book getMostPopularBook();
    public List<Book> getAllBooks();
    public List<Book> getBooksByAuthor(String author);
}
