package com.zl.gulimallcart.feign;

import com.zl.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

/**
 * @author ZhuLing
 * @date 2021/11/25 - 15:22
 */
@FeignClient("gulimall-product")
public interface ProductFeignService {
    @RequestMapping("/product/skuinfo/info/{skuId}")
    R info(@PathVariable("skuId") Long skuId);
    @RequestMapping("/product/skusaleattrvalue/skuIdInfo/{skuId}")
    R skuIdInfo(@PathVariable("skuId") Long skuId);
    @GetMapping("/product/skuinfo/{skuId}/price")
    BigDecimal getPrice(@PathVariable Long skuId);
}
