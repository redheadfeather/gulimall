package com.zl.gulimall.order.web;

import com.zl.common.utils.PageUtils;
import com.zl.common.utils.R;
import com.zl.gulimall.order.service.OrderService;
import com.zl.gulimall.order.vo.OrderConfirmVo;
import com.zl.gulimall.order.vo.OrderSubmitVo;
import com.zl.gulimall.order.vo.SubmitOrderResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author ZhuLing
 * @date 2021/11/27 - 21:27
 */
@Controller
public class OrderWebController {
    @Autowired
    OrderService orderService;
    @GetMapping("/toTrade")
    public String toTrade(Model model, HttpServletRequest request) throws ExecutionException, InterruptedException {
        OrderConfirmVo confirmVo = orderService.confirmOrder();
        model.addAttribute("confirmOrderData",confirmVo);
        return "confirm";
    }
    @PostMapping("/submitOrder")
    public String submitOrder(OrderSubmitVo vo, Model model, RedirectAttributes attributes){
        SubmitOrderResponseVo responseVo=orderService.submitOrder(vo);
        if (responseVo.getCode()==0){
            model.addAttribute("submitOrderResp",responseVo);
            return "pay";
        }else{
            String msg = "下单失败";
            switch (responseVo.getCode()) {
                case 1: msg += "令牌订单信息过期，请刷新再次提交"; break;
                case 2: msg += "订单商品价格发生变化，请确认后再次提交"; break;
                case 3: msg += "库存锁定失败，商品库存不足"; break;
            }
            attributes.addFlashAttribute("msg",msg);
            return "redirect:http://order.gulimall.com/toTrade";
        }

    }
    @GetMapping("/memberOrder.html")
    public String memberOrderPage(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, Model model){
        Map<String, Object> page = new HashMap<>();
        page.put("page",pageNum);

        R r = listWithItem(page);
//        PageUtils page1 = r.getData("page", new TypeReference<PageUtils>() {
//        });
//        List<?> list = page1.getList();
        //<a href="http://order.gulimall.com/memberOrder.html">我的订单</a>
        model.addAttribute("orders",r);
        return "list";
    }
    @GetMapping("/listWithItem")
    public R listWithItem(@RequestParam Map<String, Object> params){
        PageUtils page = orderService.queryPageWithItem(params);
        return R.ok().put("page", page);
    }
}
