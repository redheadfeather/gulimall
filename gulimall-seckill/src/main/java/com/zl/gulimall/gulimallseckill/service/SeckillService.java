package com.zl.gulimall.gulimallseckill.service;

import com.zl.gulimall.gulimallseckill.to.SeckillSkuRedisTo;

import java.util.List;

/**
 * @author ZhuLing
 * @date 2021/12/4 - 15:17
 */
public interface SeckillService {
    void uploudSeckillSkuLatest3Days();

    List<SeckillSkuRedisTo> getCurrentSeckillSkus();

    SeckillSkuRedisTo getSkuSeckilInfo(Long skuId);

    String kill(String killId, String key, Integer num) throws InterruptedException;
}
