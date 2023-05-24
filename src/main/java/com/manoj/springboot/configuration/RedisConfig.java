package com.manoj.springboot.configuration;

import com.manoj.springboot.model.Member;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory jedisConFactory= new JedisConnectionFactory();
        return jedisConFactory;
    }

    @Bean
    public RedisTemplate<Integer, Member> redisTemplate(){
        RedisTemplate<Integer,Member> template=new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    @Bean
    public CacheManager cacheManager(){
        RedisCacheConfiguration redisCacheConfiguration=RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10));
        RedisCacheManager redisCacheManager=RedisCacheManager.builder(jedisConnectionFactory()).cacheDefaults(redisCacheConfiguration).build();
        return redisCacheManager;
    }
}
