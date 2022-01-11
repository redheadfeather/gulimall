package com.zl.gulimall.gulimallseckill.scheduled;

import com.zl.gulimall.gulimallseckill.feign.CouponFeignService;
import com.zl.gulimall.gulimallseckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author ZhuLing
 * @date 2021/12/4 - 14:58
 */
@Service
@Slf4j
public class SeckillSkuScheduled {
    @Autowired
    SeckillService seckillService;
    @Autowired
    CouponFeignService couponFeignService;
    @Autowired
    RedissonClient redissonClient;
    private final String uploadLock="seckill:upload:lock";
    @Scheduled(cron = "0 25 22 * * *")
    public void uploudSeckillSkuLatest3Days(){
        RLock lock = redissonClient.getLock(uploadLock);
        lock.lock(10, TimeUnit.SECONDS);
        try {
            seckillService.uploudSeckillSkuLatest3Days();
        }finally {
            lock.unlock();
        }

    }
}
