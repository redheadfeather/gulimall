package com.zl.gulimall.gulimallseckill.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZhuLing
 * @date 2021/11/16 - 15:02
 */
@Configuration
public class MyredissonConfig {
    @Bean(destroyMethod = "shutdown")
    RedissonClient redisson(){
       Config config = new Config();
       config.useSingleServer().setAddress("redis://192.168.124.128:6377");
       return Redisson.create(config);
   }
}
