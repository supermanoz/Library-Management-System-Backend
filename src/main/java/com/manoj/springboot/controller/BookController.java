package com.manoj.springboot.controller;

import com.manoj.springboot.configuration.Message;
import com.manoj.springboot.configuration.MyResourceBundleMessageSource;
import com.manoj.springboot.exception.DoesNotExistException;
import com.manoj.springboot.dto.BookDto;
import com.manoj.springboot.response.MyResponse;
import com.manoj.springboot.service.ExcelService;
import com.manoj.springboot.serviceImpl.BookServiceImpl;
import com.manoj.springboot.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private Message message;
    @Autowired
    private MyResourceBundleMessageSource messageSource;
    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private ExcelService excelService;

    @GetMapping("/fetchAll")
    public ResponseEntity<?> getBooks(@RequestHeader("Accept-Language") String language) {
        List<Book> books=bookService.getBooks();
        if(books==null){
            return new ResponseEntity<>(new MyResponse<String>((HttpStatus.NO_CONTENT),messageSource.messageSource().getMessage("doesNotExist",null,new Locale(language))),HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new MyResponse<>((HttpStatus.OK),books),HttpStatus.OK);
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<?> getBook(@PathVariable String id,@RequestHeader("Accept-Language") String language){
        Book book=bookService.getBook(id);
        if(book!=null)
            return new ResponseEntity<>(new MyResponse<>(HttpStatus.OK,book),HttpStatus.OK);
        else
            throw new DoesNotExistException("Book "+ messageSource.messageSource().getMessage("doesNotExist",null,new Locale(language)));
    }

    @PostMapping("/save")
    public ResponseEntity<?> addBook(@RequestBody BookDto bookDto){
        Book addedBook=bookService.addBook(bookDto);
        return new ResponseEntity<>(new MyResponse<Book>(HttpStatus.OK,addedBook),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable String id, @RequestHeader("Accept-Language") String language){
        if(bookService.deleteBook(id)){
            return new ResponseEntity<>(new MyResponse<>(HttpStatus.OK,message.getDeleted()),HttpStatus.OK);
        }
        throw new DoesNotExistException(message.getDoesNotExist());
    }

    @GetMapping("/fetchMostPopular")
    public ResponseEntity<?> getMostPopularBook(){
        Book book=bookService.getMostPopularBook();
        return new ResponseEntity<>(new MyResponse<>(HttpStatus.OK,book),HttpStatus.OK);
    }

    @GetMapping("/fetchAllBooks")
    public ResponseEntity<?> getAllBooks(){
        List<Book> books=bookService.getAllBooks();
        return new ResponseEntity<>(new MyResponse<>(HttpStatus.OK,books),HttpStatus.OK);
    }

    @GetMapping("/fetchAllBooksByAuthor")
    public ResponseEntity<?> getAllBooksByAuthor(@RequestParam("author") String author){
        List<Book> books=bookService.getBooksByAuthor(author);
        return new ResponseEntity<>(new MyResponse<>(HttpStatus.OK,books),HttpStatus.OK);
    }

    @GetMapping("/exportExcel")
    public ResponseEntity<?> exportExcel(@RequestParam("fileName") String excelName) throws IOException {
//        response.setContentType("application/octet-stream");
//        response.setHeader("Content-Disposition","attachment;filename=book.xls");
//        excelService.exportExcel(response);
        excelService.exportExcel(Book.class,excelName);
        return ResponseEntity.ok().body("Excel file exported as file name: "+excelName);
    }

    @GetMapping("/importExcel")
    public ResponseEntity<?> importExcel(@RequestParam("file")MultipartFile file) throws IOException {
//        File file=new File(file);
        excelService.importExcel(Book.class,file);
        return ResponseEntity.ok().body("Done baby boo!");
    }
}
