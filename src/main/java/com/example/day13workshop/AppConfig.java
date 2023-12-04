package com.example.day13workshop;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class AppConfig {
    
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.username}")
    private String redisUser;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    @Value("${spring.redis.database}")
    private Integer redisDatabase;

    @Value("${spring.redis.password}")
    private String redisPassword;

    private Logger logger = Logger.getLogger(AppConfig.class.getName());

    @Bean("ContactRedis")
    public RedisTemplate<String, Object> template(){
        //create a redis configuration
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort);
        config.setDatabase(redisDatabase);
        
        // only set the user and pass if they are set
        if(!"NOT_SET".equals(redisUser.trim())){
            config.setUsername(redisUser);
   
            config.setPassword(redisPassword);
        }

        logger.log(Level.INFO,"Using Redis database %d".formatted(redisPort));
        logger.log(Level.INFO,"Redis password is set: %b".formatted(redisPassword != "NOT_SET"));

        JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();

        //create redis template
        RedisTemplate<String,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisFac);

        template.setKeySerializer(new StringRedisSerializer());
        //serialises a generic object to string and vice-versa
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();

        return template;
    }
    
}
