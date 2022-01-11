package com.zl.gulimall.gulimallseckill.feign;

import com.zl.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ZhuLing
 * @date 2021/12/4 - 15:20
 */
@FeignClient("gulimall-coupon")
public interface CouponFeignService {
    @GetMapping(value = "/coupon/seckillsession/Lates3DaySession")
    R getLates3DaySession();
}
