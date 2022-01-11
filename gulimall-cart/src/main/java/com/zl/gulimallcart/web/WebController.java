package com.zl.gulimallcart.web;

import com.zl.gulimallcart.interceptor.CartInterCeptor;
import com.zl.gulimallcart.service.CartService;
import com.zl.gulimallcart.to.UserInfoTo;
import com.zl.gulimallcart.vo.CartItemVo;
import com.zl.gulimallcart.vo.CartVo;
import org.bouncycastle.math.raw.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author ZhuLing
 * @date 2021/11/24 - 15:02
 */
@Controller
public class WebController {
    @Resource
    private CartService cartService;


//    @GetMapping(value = "/cart.html")
//    public String cartListPage(Model model) throws ExecutionException, InterruptedException {
//        //快速得到用户信息：id,user-key
//        UserInfoTo userInfoTo = CartInterCeptor.toThreadLocal.get();
//
//        //CartVo cartVo = cartService.getCart();
//        //model.addAttribute("cart",cartVo);
//        return "cartList";
//    }
    @RequestMapping("/addToCart")
    public String addToCart(@RequestParam Long skuId, @RequestParam Integer num, RedirectAttributes redirectAttributes) throws ExecutionException, InterruptedException {
        cartService.addToCart(skuId,num);

        redirectAttributes.addAttribute("skuId",skuId);
        return "redirect:http://cart.gulimall.com/addToCartSuccess.html";
    }
    @GetMapping("/addToCartSuccess.html")
    public String addToCartSuccessPage(@RequestParam Long skuId, Model model){
        CartItemVo cartItemVo = cartService.getCartItem(skuId);
        model.addAttribute("item",cartItemVo);
        return "success";
    }
    @GetMapping("/cart.html")
    public String cartListPage(Model model) throws ExecutionException, InterruptedException {
        CartVo cartVo = cartService.getCart();
        model.addAttribute("cart",cartVo);
        return "cartList";
    }
    @GetMapping("/checkItem")
    public String checkItem(@RequestParam("skuId") Long skuId,@RequestParam("check") Integer check){
        cartService.checkItem(skuId,check);
        return "redirect:http://cart.gulimall.com/cart.html";
    }
    @GetMapping("/countItem")
    public String countItem(@RequestParam("skuId") Long skuId,@RequestParam("num") Integer num){
        cartService.countItem(skuId,num);
        return "redirect:http://cart.gulimall.com/cart.html";
    }
    @GetMapping("/deleteItem")
    public String deleteItem(@RequestParam("skuId") Long skuId){
        cartService.deleteItem(skuId);
        return "redirect:http://cart.gulimall.com/cart.html";
    }
    @GetMapping("/currentUserCartItems")
    @ResponseBody
    public List<CartItemVo> getCurrentUserCartItems(){
        return cartService.getUserCartItems();
    }
}
