package com.kiraliza.spring.authenticaion.sso_server.config;

import com.kiraliza.spring.authenticaion.sso_server.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class RedisConfig
{
//    @Autowired
//    private RedisProperties redisProperties;
//
//    @Bean
//    public LettuceConnectionFactory lettuceConnectionFactory()
//    {
//        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());
//        if (StringUtils.hasText(redisProperties.getPassword()))
//        {
//            config.setPassword(redisProperties.getPassword());
//        }
//        return new LettuceConnectionFactory(config);
//    }
//
//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory()
//    {
//        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());
//        if (StringUtils.hasText(redisProperties.getPassword()))
//        {
//            config.setPassword(redisProperties.getPassword());
//        }
//        return new JedisConnectionFactory(config);
//    }

    @Bean
    public RedisTemplate<String, User> redisTemplate(RedisConnectionFactory redisConnectionFactory)
    {
        RedisTemplate<String, User> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        return redisTemplate;
    }
}
