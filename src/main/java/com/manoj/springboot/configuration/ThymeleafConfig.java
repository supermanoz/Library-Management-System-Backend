package com.manoj.springboot.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class ThymeleafConfig {
    private static final String MAIL_TEMPLATE_BASE_NAME="test";
    private static final String MAIL_TEMPLATE_PREFIX="/templates/";
    private static final String MAIL_TEMPLATE_SUFFIX=".html";
    private static final String UTF_8="UTF-8";

    @Bean
    public TemplateEngine emailTemplateEngine(){
        SpringTemplateEngine springTemplateEngine=new SpringTemplateEngine();
        springTemplateEngine.setTemplateEngineMessageSource(messageSource());
        springTemplateEngine.setTemplateResolver(htmlTemplateResolver());
        return springTemplateEngine;
    }

    private MessageSource messageSource() {
        final ResourceBundleMessageSource messageSource=new ResourceBundleMessageSource();
        messageSource.setBasename(MAIL_TEMPLATE_BASE_NAME);
        return messageSource;
    }

    private ITemplateResolver htmlTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver=new ClassLoaderTemplateResolver();
        templateResolver.setCacheable(false);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(UTF_8);
        templateResolver.setPrefix(MAIL_TEMPLATE_PREFIX);
        templateResolver.setSuffix(MAIL_TEMPLATE_SUFFIX);
        return templateResolver;
    }
}
