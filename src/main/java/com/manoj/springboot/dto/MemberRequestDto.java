package com.manoj.springboot.dto;

import com.manoj.springboot.enums.RoleEnum;
import lombok.Data;

import java.util.Date;

@Data
public class MemberRequestDto {

    private int memberId;
    private String name;
    private Character gender;
    private String address;
    private String email;
    private Date dob;
    private String phone;
    private RoleEnum role;
    private String password;
}
