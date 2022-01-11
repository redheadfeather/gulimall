package com.zl.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ZhuLing
 * @date 2021/11/29 - 20:20
 */
public class NoStockException extends RuntimeException {

    @Getter
    @Setter
    private Long skuId;

    public NoStockException(Long skuId) {
        super("商品id："+ skuId + "库存不足！");
    }

    public NoStockException(String msg) {
        super(msg);
    }


}