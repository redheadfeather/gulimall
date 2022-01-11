package com.zl.gulimall.product.feign;

import com.zl.common.utils.R;
import com.zl.gulimall.product.fallback.SeckillFeignServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ZhuLing
 * @date 2021/12/4 - 21:56
 */
@FeignClient(value = "gulimall-seckill",fallback = SeckillFeignServiceFallback.class)
public interface SeckillFeignService {
    @GetMapping(value = "/sku/seckill/{skuId}")
    @ResponseBody
    R getSkuSeckilInfo(@PathVariable("skuId") Long skuId);
}
