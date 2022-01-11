package com.zl.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.common.utils.PageUtils;
import com.zl.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.zl.gulimall.product.vo.SkuItemSaleAttrVo;

import java.util.List;
import java.util.Map;

/**
 * sku销售属性&值
 *
 * @author zhuling
 * @email 373598252@qq.com
 * @date 2021-11-05 15:07:17
 */
public interface SkuSaleAttrValueService extends IService<SkuSaleAttrValueEntity> {

    List<SkuItemSaleAttrVo> getSkuItemSaleAttrVo(Long spuId);

    PageUtils queryPage(Map<String, Object> params);

    List<SkuSaleAttrValueEntity> skuIdInfo(Long skuId);
}

