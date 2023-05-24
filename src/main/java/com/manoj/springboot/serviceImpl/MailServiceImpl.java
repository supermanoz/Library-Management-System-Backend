package com.manoj.springboot.serviceImpl;

import com.manoj.springboot.dto.MailRequestDto;
import com.manoj.springboot.service.MailService;
import com.manoj.springboot.service.ThymeleafTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class MailServiceImpl implements MailService {
    @Value("${spring.mail.username}")
    String from;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private ThymeleafTemplateService thymeleafTemplateService;
    @Override
    public void sendEmail(MailRequestDto mailRequestDto) throws MessagingException {
//        SimpleMailMessage message=new SimpleMailMessage();
        MimeMessage message=mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(
                message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name()
        );
        helper.setFrom(from);
        helper.setSubject(mailRequestDto.getSubject());
        helper.setTo(mailRequestDto.getTo());
        helper.setText(thymeleafTemplateService.createContent(mailRequestDto.getTemplate(),mailRequestDto.getModel()),true);
        if(mailRequestDto.getCc()!=null && mailRequestDto.getCc().length>0){
            helper.setCc(mailRequestDto.getCc());
        }
        if(mailRequestDto.getBcc()!=null && mailRequestDto.getBcc().length>0){
            helper.setBcc(mailRequestDto.getBcc());
        }
        mailSender.send(message);
        System.out.println("Mail sent baby!!");
    }
}
