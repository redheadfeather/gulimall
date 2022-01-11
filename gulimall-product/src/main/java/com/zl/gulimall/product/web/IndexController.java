package com.zl.gulimall.product.web;

import com.zl.gulimall.product.entity.CategoryEntity;
import com.zl.gulimall.product.service.CategoryService;
import com.zl.gulimall.product.vo.Catelog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author ZhuLing
 * @date 2021/11/14 - 18:24
 */
@Controller
public class IndexController {
    @Autowired
    CategoryService categoryService;
    @GetMapping({"/","/index.html"})
    public String indexPage(Model model){
        List<CategoryEntity> categoryEntityList = categoryService.getLevel1Category();
        model.addAttribute("categorys",categoryEntityList);
        return "index";
    }
    @GetMapping(value = "/index/json/catalog")
    @ResponseBody
    public Map<String, List<Catelog2Vo>> getCatalogJson() throws InterruptedException {

        Map<String, List<Catelog2Vo>> catalogJson = categoryService.getCatalogJson();

        return catalogJson;

    }
    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello";
    }
}
