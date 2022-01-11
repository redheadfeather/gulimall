package com.zl.gulimall.order.dao;

import com.zl.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 订单
 * 
 * @author zhuling
 * @email 373598252@qq.com
 * @date 2021-11-05 16:26:12
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

    void updateOrderStatus(@Param("orderSn") String orderSn,@Param("code")Integer code,@Param("payType")Integer payType);
}
