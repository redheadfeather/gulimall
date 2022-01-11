package com.zl.gulimall.gulimallsearch.feign;

import com.zl.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ZhuLing
 * @date 2021/11/19 - 0:12
 */
@FeignClient("gulimall-product")
public interface ProductFeignService {
    @GetMapping("product/attr/info/{attrId}")
    R attrInfo(@PathVariable("attrId") Long attrId);
    @GetMapping("product/brand/infos")
    public R info(@RequestParam("brandIds") List<Long> brandIds);
}
