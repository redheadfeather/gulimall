package com.zl.gulimall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ZhuLing
 * @date 2021/11/11 - 23:33
 */
@Data
public class MergeVo {
    private Long purchaseId;
    private List<Long> items;
}
