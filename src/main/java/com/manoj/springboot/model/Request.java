package com.manoj.springboot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name="request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;
    @Column(name="request_date")
    private Date requestDate=new Timestamp(System.currentTimeMillis());
    @ManyToOne
    @JoinColumn(name="isbn",referencedColumnName = "isbn",nullable = false)
    private Book book;
    @JoinColumn(name="member_id",referencedColumnName = "member_id",nullable = false)
    @ManyToOne
    private Member member;
    @Column(name="request_due")
    private Boolean requestDue=true;

    public Request(Member member,Book book){
        this.member=member;
        this.book=book;
    }
}
