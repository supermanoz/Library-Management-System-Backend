package com.manoj.springboot.service;

import com.manoj.springboot.dto.MailRequestDto;

import javax.mail.MessagingException;

public interface MailService {
    public void sendEmail(MailRequestDto mailRequestDto) throws MessagingException;
}
