package com.zl.gulimall.product.web;

import com.zl.gulimall.product.service.SkuInfoService;
import com.zl.gulimall.product.vo.SkuItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ZhuLing
 * @date 2021/11/20 - 16:38
 */
@Controller
public class ItemController {
    @Autowired
    SkuInfoService skuInfoService;
    @RequestMapping("/item")
    public String item(){
        return "item";
    }
    @RequestMapping("/{skuId}.html")
    public String skuItem(@PathVariable("skuId") Long skuId, Model model){
        SkuItemVO itemVO = skuInfoService.item(skuId);
        model.addAttribute("item",itemVO);
        return "item";
    }
}
