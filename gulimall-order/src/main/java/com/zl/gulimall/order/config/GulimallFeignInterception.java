package com.zl.gulimall.order.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author ZhuLing
 * @date 2021/11/28 - 15:17
 */
@Configuration
public class GulimallFeignInterception {
    @Bean("requestInterceptor")
    public RequestInterceptor requestInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (requestAttributes!=null){
                    if (requestAttributes.getRequest()!=null){
                        requestTemplate.header("Cookie",requestAttributes.getRequest().getHeader("Cookie"));
                    }
                }
            }
        };
    }
}
