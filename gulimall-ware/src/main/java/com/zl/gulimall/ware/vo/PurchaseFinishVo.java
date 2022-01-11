package com.zl.gulimall.ware.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author ZhuLing
 * @date 2021/11/12 - 14:04
 */
@Data
public class PurchaseFinishVo {
    @NotNull
    private Long id;
    private List<PurchaseItemVo> items;
}
