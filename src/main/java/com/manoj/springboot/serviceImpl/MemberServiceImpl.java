package com.manoj.springboot.serviceImpl;

import com.manoj.springboot.configuration.Message;
import com.manoj.springboot.dto.MemberRequestDto;
import com.manoj.springboot.dto.MemberResponseDto;
import com.manoj.springboot.configuration.MyResourceBundleMessageSource;
import com.manoj.springboot.model.Member;
import com.manoj.springboot.enums.RoleEnum;
import com.manoj.springboot.exception.DoesNotExistException;
import com.manoj.springboot.repository.MemberRepository;
import com.manoj.springboot.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private Message message;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MyResourceBundleMessageSource messageSource;
    private final String imageFilepath="F:/Java/Spring-Boot/Spring-LMS/src/main/resources/images/";
    Logger logger= LoggerFactory.getLogger(MemberServiceImpl.class);

    @Override
    public List<MemberResponseDto> getAllMembers(){
        List<MemberResponseDto> members=new ArrayList<>();
        memberRepository.findAll().forEach((member)->
                members.add(memberToMemberResponse(member))
        );
        return members;
    }

    @Override
    public MemberResponseDto getMember(int id,String language){
        if(!memberRepository.existsById(id)){
            throw new DoesNotExistException(messageSource.messageSource().getMessage("doesNotExist",null,new Locale(language)));
        }
        logger.trace("Redis Cache Miss! Sad!");
        return memberToMemberResponse(memberRepository.findByMemberId(id));
    }

    @Override
    public MemberResponseDto addMember(MemberRequestDto memberRequestDto){
        if(memberRequestDto.getMemberId()!=0){ //i.e. updation!
//            Member member=new Member(memberDto.getMemberId(),memberDto.getName(),memberDto.getGender(),memberDto.getAddress(),memberDto.getEmail(),memberDto.getDob(),memberDto.getPhone(),memberDto.getRole());
            Member repoMember=memberRepository.findByMemberId(memberRequestDto.getMemberId());
            Member member= Member.builder()
                    .memberId(memberRequestDto.getMemberId())
                    .name(memberRequestDto.getName())
                    .dob(memberRequestDto.getDob())
                    .email(repoMember.getEmail())
                    .address(memberRequestDto.getAddress())
                    .gender(memberRequestDto.getGender())
                    .phone(memberRequestDto.getPhone())
                    .role(memberRequestDto.getRole())
                    .password(BCrypt.hashpw(memberRequestDto.getPassword(),BCrypt.gensalt()))
                    .build();
            return memberToMemberResponse(memberRepository.save(member));
        }
//        Member member=new Member(memberDto.getName(),memberDto.getGender(),memberDto.getAddress(),memberDto.getEmail(),memberDto.getDob(),memberDto.getPhone(),memberDto.getRole());
        Member member= Member.builder()
                .name(memberRequestDto.getName())
                .email(memberRequestDto.getEmail())
                .dob(memberRequestDto.getDob())
                .address(memberRequestDto.getAddress())
                .gender(memberRequestDto.getGender())
                .phone(memberRequestDto.getPhone())
                .role(memberRequestDto.getRole())
                .password(BCrypt.hashpw(memberRequestDto.getPassword(),BCrypt.gensalt()))
                .build();

        return memberToMemberResponse(memberRepository.save(member));
    }

    @Override
    public boolean deleteMember(int memberId){
        if(!memberRepository.existsById(memberId)){
            throw new DoesNotExistException("The member does not exist!");
        }
        memberRepository.deleteById(memberId);
        return true;
    }

    @Override
    public MemberResponseDto uploadImage(MultipartFile file,Integer memberId) throws IOException {
//        // ### Using BLOB Concept ###
//        if(!memberRepository.existsById(memberId)){
//            throw new DoesNotExistException("Member "+message.getDoesNotExist());
//        }
//        Member member=memberRepository.findByMemberId(memberId);
//        byte[] imageBytes=file.getBytes();
//        Byte[] image=new Byte[imageBytes.length];
//        for(int i=0;i<imageBytes.length;i++){
//            image[i]= imageBytes[i];
//        }
//        member.setImageData(image);
//        return memberRepository.save(member);

//       ### Using Filepath Concept ###
        if(!memberRepository.existsById(memberId)){
            throw new DoesNotExistException("Member "+message.getDoesNotExist());
        }
        file.transferTo(new File(imageFilepath+file.getOriginalFilename()));
        Member member=memberRepository.findByMemberId(memberId);
        member.setImagePath(imageFilepath+file.getOriginalFilename());
        return memberToMemberResponse(memberRepository.save(member));
    }

    @Override
    public byte[] downloadImage(Integer memberId) throws IOException {
////       ### Using BLOB Concept ###
//        Byte[] imageData=memberRepository.findByMemberId(memberId).getImageData();
//        byte[] imageBytes=new byte[imageData.length];
//        for(int i=0;i<imageBytes.length;i++){
//            imageBytes[i]=imageData[i];
//        }
//        return imageBytes;

//        ### Using Filepath concept ###
        String filepath=memberRepository.findByMemberId(memberId).getImagePath();
        byte[] imageBytes= Files.readAllBytes(new File(filepath).toPath());
        return imageBytes;
    }

    public RoleEnum getRoleEnum(String role){
        return switch (role) {
            case "student" -> RoleEnum.STUDENT;
            case "librarian" -> RoleEnum.LIBRARIAN;
            case "staff" -> RoleEnum.STAFF;
            default -> null;
        };
    }

    public MemberResponseDto memberToMemberResponse(Member member){
        return MemberResponseDto.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .dob(member.getDob())
                .gender(member.getGender())
                .address(member.getAddress())
                .email(member.getEmail())
                .phone(member.getPhone())
                .role(member.getRole())
                .imagePath(member.getImagePath())
                .build();
    }
}
