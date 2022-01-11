package com.zl.gulimall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ZhuLing
 * @date 2021/11/29 - 19:40
 */
@Data
public class WareSkuLockVo {
    private String orderSn;

    /** 需要锁住的所有库存信息 **/
    private List<OrderItemVo> locks;
}
