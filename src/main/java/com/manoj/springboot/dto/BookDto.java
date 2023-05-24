package com.manoj.springboot.dto;

import lombok.Data;
import java.util.Date;

@Data
public class BookDto {
    private String isbn;
    private String title;
    private String author;
    private Date publicationDate;
    private String genre;
    private int quantity;
    private int checkoutDuration;
    private long publisherId;
}
