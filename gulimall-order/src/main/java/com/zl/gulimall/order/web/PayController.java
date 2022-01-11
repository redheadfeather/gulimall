package com.zl.gulimall.order.web;

import com.alipay.api.AlipayApiException;
import com.zl.gulimall.order.config.AlipayTemplate;
import com.zl.gulimall.order.service.OrderService;
import com.zl.gulimall.order.vo.PayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ZhuLing
 * @date 2021/12/2 - 21:22
 */
@Controller
public class PayController {
    @Autowired
    AlipayTemplate alipayTemplate;
    @Autowired
    OrderService orderService;
    @GetMapping(value = "/payOrder",produces = "text/html")
    @ResponseBody
    public String payOrder(@RequestParam("orderSn") String orderSn) throws AlipayApiException {
        PayVo payVo = orderService.getOrderPay(orderSn);
        String pay=alipayTemplate.pay(payVo);
        return pay;
    }
}
