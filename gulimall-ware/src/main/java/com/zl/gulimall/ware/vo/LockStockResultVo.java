package com.zl.gulimall.ware.vo;

import lombok.Data;

/**
 * @author ZhuLing
 * @date 2021/11/29 - 19:36
 */
@Data
public class LockStockResultVo {
    private Long skuId;

    private Integer num;

    /** 是否锁定成功 **/
    private Boolean locked;

}
