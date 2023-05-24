package com.manoj.springboot.dto;

import com.manoj.springboot.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDto implements Serializable {
    private int memberId;
    private String name;
    private Character gender;
    private String address;
    private String email;
    private Date dob;
    private String phone;
    private RoleEnum role;
    private String imagePath;
}
