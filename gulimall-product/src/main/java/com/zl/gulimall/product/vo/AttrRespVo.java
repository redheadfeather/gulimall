package com.zl.gulimall.product.vo;

import lombok.Data;

/**
 * @author ZhuLing
 * @date 2021/11/9 - 21:04
 */
@Data
public class AttrRespVo extends AttrVo{
    private String catelogName;
    private String groupName;
    private Long[] catelogPath;
}
