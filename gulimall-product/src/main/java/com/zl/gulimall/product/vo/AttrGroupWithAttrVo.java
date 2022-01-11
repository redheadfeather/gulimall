package com.zl.gulimall.product.vo;

import com.zl.gulimall.product.entity.AttrEntity;
import lombok.Data;

import java.util.List;

/**
 * @author ZhuLing
 * @date 2021/11/10 - 21:14
 */
@Data
public class AttrGroupWithAttrVo {
    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;
    private List<AttrEntity> attrs;
}
