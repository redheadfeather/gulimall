package com.zl.gulimall;

import com.zl.gulimall.product.entity.BrandEntity;
import com.zl.gulimall.product.service.BrandService;
import com.zl.gulimall.product.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;


@SpringBootTest
class GulimallProductApplicationTests {
    @Autowired
    BrandService brandService;
    @Autowired
    CategoryService categoryService;
    @Test
    void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("huawei");
        brandService.save(brandEntity);
        System.out.println("save successfully!");
    }
    @Test
    void findPath(){
        System.out.println(Arrays.asList(categoryService.findCatelogPath(100L)));
    }

}
