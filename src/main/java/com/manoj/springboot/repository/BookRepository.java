package com.manoj.springboot.repository;

import com.manoj.springboot.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BookRepository extends CrudRepository<Book,String> {
    public Book findByIsbn(String isbn);
    public void deleteByIsbn(String isbn);
    public boolean existsByTitle(String title);
    public List<Book> findAllByIsDeleted(boolean isDeleted);
    public Book findByIsbnAndIsDeleted(String isbn,boolean isDeleted);

    public boolean existsByIsbnAndIsDeleted(String isbn,boolean isDeleted);
    @Query(value="select book.*,count(book.isbn) as count_book from book join burrow on burrow.isbn=book.isbn group by book.isbn order by count_book desc limit 1",nativeQuery = true)
    public Book getMostPopularBook();

    @Query(name="selectOne",nativeQuery = true)
    public List<Book> getAllBooksByAuthor(@Param("name") String name);
}
