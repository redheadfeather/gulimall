package com.zl.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 属性&属性分组关联
 * 
 * @author zhuling
 * @email 373598252@qq.com
 * @date 2021-11-05 15:07:17
 */
@Data
@TableName("pms_attr_attrgroup_relation")
public class AttrAttrgroupRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 属性id
	 */
	private Long attrId;
	/**
	 * 属性分组id
	 */
	private Long attrGroupId;
	/**
	 * 属性组内排序
	 */
	private Integer attrSort;

}
