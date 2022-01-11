package com.zl.gulimall.product.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ZhuLing
 * @date 2021/11/20 - 17:09
 */
@Data
public class SkuItemSaleAttrVo {
    private Long attrId;

    private String attrName;
    //private String attrValues;
    private List<AttrValueWithSkuIdVo> attrValues;
}
