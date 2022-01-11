package com.zl.gulimall.gulimallsearch.controller;

import com.zl.common.exception.BizCodeEnum;
import com.zl.common.to.es.SkuEsModel;
import com.zl.common.utils.R;
import com.zl.gulimall.gulimallsearch.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author ZhuLing
 * @date 2021/11/14 - 13:49
 */
@Slf4j
@RequestMapping("/search")
@RestController
public class ElasticSaveController {
    @Autowired
    ProductSaveService productSaveService;
    @PostMapping("/save/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels){
        boolean b = false;
        try {
            b = productSaveService.productStatusUp(skuEsModels);
        } catch (IOException e) {
            log.error("商品上架错误：{}",e);
            return R.error(BizCodeEnum.PRODUCT_UP_EXCEPTION.getErrorCode(),
                    BizCodeEnum.PRODUCT_UP_EXCEPTION.getErrorMessage());
        }
        if (b){
            return R.ok();
        }else {
            return R.error(BizCodeEnum.PRODUCT_UP_EXCEPTION.getErrorCode(),
                    BizCodeEnum.PRODUCT_UP_EXCEPTION.getErrorMessage());
        }

    }
}
