package com.zl.gulimall.order.service.impl;

import com.rabbitmq.client.Channel;
import com.zl.gulimall.order.entity.OrderReturnReasonEntity;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.common.utils.PageUtils;
import com.zl.common.utils.Query;

import com.zl.gulimall.order.dao.OrderItemDao;
import com.zl.gulimall.order.entity.OrderItemEntity;
import com.zl.gulimall.order.service.OrderItemService;


@Service("orderItemService")
public class OrderItemServiceImpl extends ServiceImpl<OrderItemDao, OrderItemEntity> implements OrderItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderItemEntity> page = this.page(
                new Query<OrderItemEntity>().getPage(params),
                new QueryWrapper<OrderItemEntity>()
        );

        return new PageUtils(page);
    }
//    @RabbitListener(queues = "hello-java-queue")
//    public void receiveMessage(Message message, OrderReturnReasonEntity content, Channel channel){
//        byte[] body = message.getBody();
//        MessageProperties properties = message.getMessageProperties();
//        System.out.println("message received "+message+"===>"+content);
//    }
}