package com.manoj.springboot.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class MyResourceBundleMessageSource {

    @Bean
    public SessionLocaleResolver sessionLocaleResolver(){
        SessionLocaleResolver slr=new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.ENGLISH);
        return slr;
    }
    public ResourceBundleMessageSource messageSource(){
        ResourceBundleMessageSource rs=new ResourceBundleMessageSource();
        rs.setBasename("message");
        return rs;
    }
}
