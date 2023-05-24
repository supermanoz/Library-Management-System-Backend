package com.manoj.springboot.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class MailRequestDto {
    private String to;
    private String subject;
    private Map<String,Object> model;
    private String template;
    private String[] cc;
    private String[] bcc;
}
