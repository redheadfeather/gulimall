package com.zl.gulimallauthserver.feign;

import com.zl.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ZhuLing
 * @date 2021/11/21 - 23:44
 */
@FeignClient("gulimall-third-party")
public interface SmsFeignService {
    @GetMapping("/sms/send")
    public R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code);
}
