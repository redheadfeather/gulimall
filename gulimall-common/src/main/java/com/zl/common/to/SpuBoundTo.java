package com.zl.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ZhuLing
 * @date 2021/11/11 - 14:42
 */
@Data
public class SpuBoundTo {
    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
