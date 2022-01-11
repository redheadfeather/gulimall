package com.zl.common.to;

/**
 * @author ZhuLing
 * @date 2021/11/29 - 20:27
 */

import lombok.Data;

@Data
public class StockLockedTo {
    /** 库存工作单的id **/
    private Long id;

    /** 工作单详情的所有信息 **/
    private StockDetailTo detailTo;
}
