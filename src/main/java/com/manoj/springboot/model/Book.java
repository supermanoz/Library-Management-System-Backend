package com.manoj.springboot.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name="book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedNativeQueries(value={
        @NamedNativeQuery(name="selectAll",query="select * from book"),
        @NamedNativeQuery(name="selectOne",query="select * from book where author= :name",resultSetMapping = "Mapping.Book")
}
)
@SqlResultSetMapping(name="Mapping.Book",classes =@ConstructorResult(targetClass = Book.class, columns = {
        @ColumnResult(name="isbn"),
        @ColumnResult(name="title"),
        @ColumnResult(name="author"),
        @ColumnResult(name="publication_date"),
        @ColumnResult(name="genre"),
        @ColumnResult(name="quantity"),
        @ColumnResult(name="checkout_duration"),
        @ColumnResult(name="publisher_publisher_id")
}))
//@SqlResultSetMapping(name="Mapping.Book",classes=@ConstructorResult(targetClass = Book.class,columns={
//        @ColumnResult(name="isbn")
//}))
public class Book extends Auditable{

    @Id
    private String isbn;
    @Column(length=50,nullable = false,unique = true)
    private String title;
    private String author;
    private Date publicationDate;
    private String genre;
    private Integer quantity;
    @Column(name="checkout_duration")
    @Max(21)
    private Integer checkoutDuration;
    @ManyToOne
    private Publisher publisher;
    @Column(name="is_deleted")
    private Boolean isDeleted=false;

    public Book(String isbn, String title, String author, Date publicationDate, String genre, Integer quantity, Integer checkoutDuration, Long publisherId) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
        this.genre = genre;
        this.quantity = quantity;
        this.checkoutDuration = checkoutDuration;
        this.publisher = new Publisher(publisherId,"","");
    }

    public Book(String isbn, String title, String author, Date publicationDate, String genre, Integer quantity, Integer checkoutDuration, BigInteger publisherId) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
        this.genre = genre;
        this.quantity = quantity;
        this.checkoutDuration = checkoutDuration;
        this.publisher = new Publisher(publisherId.longValue(),"","");
    }

    public Book(String isbn){
        this.isbn=isbn;
    }
}
