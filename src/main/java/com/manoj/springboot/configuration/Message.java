package com.manoj.springboot.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:message.properties")
@ConfigurationProperties(prefix="")
@Data
@NoArgsConstructor
public class Message {
    private String inserted;
    private String updated;
    private String deleted;
    private String alreadyExists;
    private String doesNotExist;
    private String emptyField;
    private String notFound;
}
