package com.zl.common.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ZhuLing
 * @date 2021/11/11 - 14:57
 */
@Data
public class SkuReductionTo {
    private Long skuId;
    private Integer fullCount;
    private BigDecimal discount;
    private int countStatus;
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private int priceStatus;
    private List<MemberPrice> memberPrice;
}
