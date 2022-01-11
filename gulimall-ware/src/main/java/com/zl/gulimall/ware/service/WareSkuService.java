package com.zl.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rabbitmq.client.Channel;
import com.zl.common.to.OrderTo;
import com.zl.common.to.StockLockedTo;
import com.zl.common.utils.PageUtils;
import com.zl.gulimall.ware.entity.WareSkuEntity;
import com.zl.common.to.SkuHasStockVo;
import com.zl.gulimall.ware.vo.WareSkuLockVo;
import org.springframework.amqp.core.Message;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author zhuling
 * @email 373598252@qq.com
 * @date 2021-11-05 16:36:29
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);

    List<SkuHasStockVo> getSkusHasStock(List<Long> skuIds);

    Boolean orderLockStock(WareSkuLockVo vo);
    void unlockStock(StockLockedTo to);

    @Transactional(rollbackFor = Exception.class)
    void unlockStock(OrderTo orderTo);
}

