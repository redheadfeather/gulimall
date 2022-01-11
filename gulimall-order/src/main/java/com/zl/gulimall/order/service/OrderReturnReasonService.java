package com.zl.gulimall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.common.utils.PageUtils;
import com.zl.gulimall.order.entity.OrderReturnReasonEntity;

import java.util.Map;

/**
 * 退货原因
 *
 * @author zhuling
 * @email 373598252@qq.com
 * @date 2021-11-05 16:26:12
 */
public interface OrderReturnReasonService extends IService<OrderReturnReasonEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

