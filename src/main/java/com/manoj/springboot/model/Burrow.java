package com.manoj.springboot.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="burrow")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Burrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long burrowId;
    @Column(name="burrow_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date burrowDate=new Date();
    @Column(name="returned_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date returnedDate;
//    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
    @ManyToOne
    @JoinColumn(name="isbn",referencedColumnName = "isbn",nullable = false)
    private Book book;
    @JoinColumn(name="member_id",referencedColumnName = "member_id",nullable = false)
//    @ManyToOne(fetch=FetchType.LAZY,cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
    @ManyToOne
    private Member member;
    @Column(name="fine_due")
    private Boolean fineDue=false;
    private Double fineAmount;
    private Boolean isReturned=false;
    private String remarks;

    public Burrow(Member member,Book book){
        this.member=member;
        this.book=book;
    }
}
