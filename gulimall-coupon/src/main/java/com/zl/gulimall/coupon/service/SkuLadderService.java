package com.zl.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.common.utils.PageUtils;
import com.zl.gulimall.coupon.entity.SkuLadderEntity;

import java.util.Map;

/**
 * 商品阶梯价格
 *
 * @author zhuling
 * @email 373598252@qq.com
 * @date 2021-11-05 16:32:45
 */
public interface SkuLadderService extends IService<SkuLadderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

