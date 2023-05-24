package com.manoj.springboot.service;

import com.manoj.springboot.dto.MemberRequestDto;
import com.manoj.springboot.dto.MemberResponseDto;
import com.manoj.springboot.model.Member;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface MemberService {
    public List<MemberResponseDto> getAllMembers();
    public MemberResponseDto getMember(int id, String language);
    public MemberResponseDto addMember(MemberRequestDto memberRequestDto) throws MessagingException;
    public boolean deleteMember(int memberId);
    public MemberResponseDto uploadImage(MultipartFile file,Integer memberID) throws IOException;
    public byte[] downloadImage(Integer memberId) throws IOException;
}
