package com.manoj.springboot.model;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.manoj.springboot.enums.RoleEnum;
import lombok.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name="member")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member{
    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "member_generator")
    @SequenceGenerator(name="member_generator",sequenceName = "member_seq",allocationSize = 1,initialValue = 100)
    private Integer memberId;
    @Column(name="username")
    @NotBlank
    private String name;
    @Column(columnDefinition = "VARCHAR(1) CHECK (gender IN ('m', 'f'))")
    private Character gender;
    private String address;
    @Column(unique = true,nullable = false)
    @Email(message ="Invalid Email baby!")
    private String email;
    @Past(message = "Date of birth must be in past!")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dob;
    @Size(min=10)
    private String phone;
    @Column(name="issued_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date issuedDate=new Timestamp(System.currentTimeMillis());
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(9) CHECK (role IN ('LIBRARIAN','STUDENT','STAFF'))")
    private RoleEnum role;
    @Size(min=4)
    private String password;
    private boolean enabled=true;
    @Lob
    private Byte[] imageData;
    private String imagePath;

    public Member(Integer memberId){
        this.memberId=memberId;
    }

    public Member(Integer memberId,String name,Character gender,String address, String email, Date dob,String phone, RoleEnum role){
        this.memberId=memberId;
        this.name=name;
        this.gender=gender;
        this.address=address;
        this.email=email;
        this.dob=dob;
        this.phone=phone;
        this.role=role;
    }

    public Member(String name,Character gender,String address, String email, Date dob,String phone, RoleEnum role){
        this.name=name;
        this.gender=gender;
        this.address=address;
        this.email=email;
        this.dob=dob;
        this.phone=phone;
        this.role=role;
    }
}
