package com.zl.gulimall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.common.utils.PageUtils;
import com.zl.gulimall.order.entity.OrderSettingEntity;

import java.util.Map;

/**
 * 订单配置信息
 *
 * @author zhuling
 * @email 373598252@qq.com
 * @date 2021-11-05 16:26:12
 */
public interface OrderSettingService extends IService<OrderSettingEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

