package com.zl.gulimallcart.config;

import com.zl.gulimallcart.interceptor.CartInterCeptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ZhuLing
 * @date 2021/11/24 - 22:01
 */
@Configuration
public class GulimallWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CartInterCeptor()).addPathPatterns("/**");
    }
}
