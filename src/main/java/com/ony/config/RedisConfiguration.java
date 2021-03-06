package com.ony.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author Tony
 * @date 2021/8/19
 */
@Configuration
@EnableCaching
public class RedisConfiguration extends CachingConfigurerSupport {

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getSimpleName());
            sb.append("-");
            sb.append(method.getName());
            for (Object obj : params) {
                if (obj != null) {
                    sb.append("-").append(obj);
                }
            }
            return sb.toString();
        };
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = this.jackson2JsonRedisSerializer();

        // ???????????????
        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);

        // ??????key????????? String
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // ?????????????????? jackson
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // hashKey????????? String
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // hash???????????? jackson
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }


    @Bean
    RedisCacheManager redisCacheManager(RedisConnectionFactory factory) {

        RedisSerializer<String> redisSerializer = new StringRedisSerializer();

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                // ??????????????????
                .entryTtl(Duration.ofHours(1))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(this.jackson2JsonRedisSerializer()))
                .disableCachingNullValues();

        return RedisCacheManager.builder(factory).cacheDefaults(redisCacheConfiguration).build();
    }


    @Bean
    Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer() {

        Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(mapper);

        return jackson2JsonRedisSerializer;
    }

}
