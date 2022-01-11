package com.zl.gulimall.product.fallback;

import com.zl.common.exception.BizCodeEnum;
import com.zl.common.utils.R;
import com.zl.gulimall.product.feign.SeckillFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author ZhuLing
 * @date 2021/12/5 - 23:21
 */
@Component
@Slf4j
public class SeckillFeignServiceFallback implements SeckillFeignService {

    @Override
    public R getSkuSeckilInfo(Long skuId) {
        log.info("调用熔断方法");
        return R.error(BizCodeEnum.TO_MANY_REQUEST.getErrorCode(),BizCodeEnum.TO_MANY_REQUEST.getErrorMessage());
    }
}
