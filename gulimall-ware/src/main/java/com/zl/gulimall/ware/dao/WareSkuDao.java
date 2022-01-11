package com.zl.gulimall.ware.dao;

import com.zl.gulimall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.gulimall.ware.vo.SkuStockVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品库存
 * 
 * @author zhuling
 * @email 373598252@qq.com
 * @date 2021-11-05 16:36:29
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    void addStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("skuNum") Integer skuNum);

    Long getSkuStock(@Param("skuId") Long skuId);
    List<Long> listWareIdHasSkuStock(@Param("skuId") Long skuId);

    Long lockSkuStock(@Param("skuId")Long skuId,@Param("wareId") Long wareId,@Param("num") Integer num);

    void unLockStock(@Param("skuId")Long skuId, @Param("wareId")Long wareId, @Param("num")Integer num);
}
