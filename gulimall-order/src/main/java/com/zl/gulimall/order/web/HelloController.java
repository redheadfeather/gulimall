package com.zl.gulimall.order.web;

import com.rabbitmq.client.Channel;
import com.zl.gulimall.order.entity.OrderEntity;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @author ZhuLing
 * @date 2021/11/27 - 20:07
 */
@Controller
public class HelloController {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @GetMapping("/{page}.html")
    public String listPage(@PathVariable("page") String page){
        return page;
    }
    @GetMapping("/test/createOrder")
    @ResponseBody
    public String createOrderTest(){
        OrderEntity entity = new OrderEntity();
        entity.setOrderSn("1122");
        System.out.println("发消息了！");
        rabbitTemplate.convertAndSend("order-event-exchange","order.create.order",entity);
        return "ok!!";
    }
//    @RabbitListener(queues = "order.release.queue")
//    public void listener(OrderEntity orderEntity, Channel channel, Message message) throws IOException {
//        System.out.println("收到过期订单信息"+orderEntity.getOrderSn());
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//    }
}
