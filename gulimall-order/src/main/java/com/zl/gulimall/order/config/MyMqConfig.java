package com.zl.gulimall.order.config;

import com.rabbitmq.client.Channel;
import com.zl.gulimall.order.entity.OrderEntity;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhuLing
 * @date 2021/12/1 - 15:03
 */
@Configuration
public class MyMqConfig {
    @Bean
    public Queue orderDelayQueue(){
        //String name, boolean durable, boolean exclusive, boolean autoDelete, @Nullable Map<String, Object> arguments
        Map<String,Object> arguements = new HashMap<>();
        arguements.put("x-dead-letter-exchange","order-event-exchange");
        arguements.put("x-dead-letter-routing-key","order.release.order");
        arguements.put("x-message-ttl",60000L);
        return new Queue("order.delay.queue",true,false,false,arguements);
    }
    @Bean
    public Queue orderReleaseQueue(){
        return new Queue("order.release.order.queue",true,false,false);
    }
    @Bean
    public Exchange orderEventEnchange(){
        //String name, boolean durable, boolean autoDelete, Map<String, Object> arguments
        return new TopicExchange("order-event-exchange",true,false);
    }
    @Bean
    public Binding orderCreateOrderBinding(){
       // String destination, Binding.DestinationType destinationType, String exchange,
       // String routingKey, @Nullable Map<String, Object> arguments
       return new Binding("order.delay.queue", Binding.DestinationType.QUEUE,"order-event-exchange",
                "order.create.order",null);
    }
    @Bean
    public Binding orderReleaseOrderBinding(){
        return new Binding("order.release.order.queue", Binding.DestinationType.QUEUE,"order-event-exchange",
                "order.release.order",null);
    }
    @Bean
    public Binding orderReleaseOtherBinding(){
        return new Binding("stock.release.stock.queue", Binding.DestinationType.QUEUE,"order-event-exchange",
                "order.release.other.#",null);
    }
    /**
     * 商品秒杀队列
     * @return
     */
    @Bean
    public Queue orderSecKillOrrderQueue() {
        Queue queue = new Queue("order.seckill.order.queue", true, false, false);
        return queue;
    }

    @Bean
    public Binding orderSecKillOrrderQueueBinding() {
        //String destination, DestinationType destinationType, String exchange, String routingKey,
        // 			Map<String, Object> arguments
        Binding binding = new Binding(
                "order.seckill.order.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.seckill.order",
                null);

        return binding;
    }
}
