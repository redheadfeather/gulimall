package com.zl.gulimall.product.feign;

import com.zl.common.to.SkuReductionTo;
import com.zl.common.to.SpuBoundTo;
import com.zl.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author ZhuLing
 * @date 2021/11/11 - 14:25
 */
@FeignClient("gulimall-coupon")
public interface CouponFeignService {
    //@RequestMapping("/coupon/spubounds")
    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);
    @PostMapping("/coupon/skufullreduction/saveInfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);

}
