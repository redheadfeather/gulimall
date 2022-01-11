package com.zl.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.zl.common.valid.AddGroup;
import com.zl.common.valid.ListValue;
import com.zl.common.valid.UpdateGroup;
import com.zl.common.valid.UpdateShowStatusGroup;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;


/**
 * 品牌
 * 
 * @author zhuling
 * @email 373598252@qq.com
 * @date 2021-11-05 15:07:17
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@NotNull(message = "修改时Id不能为空",groups = UpdateGroup.class)
	@Null(message = "新增时ID必须为空",groups = AddGroup.class)
	@TableId
	private Long brandId;
	/**
	 * 品牌名
	 */
	@NotBlank(message = "品牌名必须提交",groups ={AddGroup.class,UpdateGroup.class})
	private String name;
	/**
	 * 品牌logo地址
	 */
	@NotBlank(groups ={AddGroup.class})
	@URL(message = "必须是合法的url地址",groups ={AddGroup.class,UpdateGroup.class})
	private String logo;
	/**
	 * 介绍
	 */
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@NotNull(groups = {AddGroup.class, UpdateShowStatusGroup.class})
	@ListValue(vals={0,1},groups = {AddGroup.class, UpdateShowStatusGroup.class})
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	@NotEmpty(groups ={AddGroup.class})
	@Pattern(regexp = "^[a-zA-Z]$",message = "必须是一个字母",groups ={AddGroup.class,UpdateGroup.class})
	private String firstLetter;
	/**
	 * 排序
	 */
	@NotNull(groups ={AddGroup.class})
	@Min(value = 0,message="排序字段要大于0",groups ={AddGroup.class,UpdateGroup.class})
	private Integer sort;

}
