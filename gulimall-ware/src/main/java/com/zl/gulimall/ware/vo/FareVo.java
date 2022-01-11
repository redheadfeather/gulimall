package com.zl.gulimall.ware.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ZhuLing
 * @date 2021/11/29 - 0:32
 */
@Data
public class FareVo {
    private MemberAddressVo address;

    private BigDecimal fare;
}
