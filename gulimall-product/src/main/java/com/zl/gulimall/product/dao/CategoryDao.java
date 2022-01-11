package com.zl.gulimall.product.dao;

import com.zl.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author zhuling
 * @email 373598252@qq.com
 * @date 2021-11-05 15:07:17
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
