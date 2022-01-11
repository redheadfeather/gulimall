package com.zl.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ZhuLing
 * @date 2021/12/5 - 17:19
 */
@Data
public class SeckillOrderTo {
    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 活动场次id
     */
    private Long promotionSessionId;
    /**
     * 商品id
     */
    private Long skuId;
    /**
     * 秒杀价格
     */
    private BigDecimal seckillPrice;

    /**
     * 购买数量
     */
    private Integer num;

    /**
     * 会员ID
     */
    private Long memberId;
}
