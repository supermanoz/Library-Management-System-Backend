package com.manoj.springboot.service;

import java.util.Map;

public interface ThymeleafTemplateService {
    public String createContent (String template, Map<String,Object> variables);
}
