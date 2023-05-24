package com.manoj.springboot.serviceImpl;

import com.manoj.springboot.service.ThymeleafTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceResourceBundle;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Map;

@Service
public class ThymeleafTemplateServiceImpl implements ThymeleafTemplateService {

    @Autowired
    private TemplateEngine emailTemplateEngine;

    @Override
    public String createContent(String template, Map<String, Object> variables) {
        final Context context=new Context();
        context.setVariables(variables);
        return emailTemplateEngine.process(template,context);
    }
}
