package com.zl.gulimall.product.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.zl.gulimall.product.entity.ProductAttrValueEntity;
import com.zl.gulimall.product.service.ProductAttrValueService;
import com.zl.gulimall.product.vo.AttrRespVo;
import com.zl.gulimall.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zl.gulimall.product.service.AttrService;
import com.zl.common.utils.PageUtils;
import com.zl.common.utils.R;



/**
 * 商品属性
 *
 * @author zhuling
 * @email 373598252@qq.com
 * @date 2021-11-05 16:01:47
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;
    @Autowired
    private ProductAttrValueService productAttrValueService;
    @RequestMapping("/{attrtype}/list/{catelogId}")
    public R baseAttrList(@RequestParam Map<String,Object> params,
                          @PathVariable(value = "catelogId") Long catelogId,
                          @PathVariable(value = "attrtype") String attrtype){
        PageUtils page = attrService.queryBaseAttrPage(params,catelogId,attrtype);
        return R.ok().put("page",page);
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:attr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }
    @GetMapping("/base/listforspu/{spuId}")
    public R baseAttrList(@PathVariable Long spuId){
        List<ProductAttrValueEntity> productAttrValueEntities = productAttrValueService.baseAttrListForSpu(spuId);
        return R.ok().put("data",productAttrValueEntities);
    }
    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    //@RequiresPermissions("product:attr:info")
    public R info(@PathVariable("attrId") Long attrId){
		//AttrEntity attr = attrService.getById(attrId);
        AttrRespVo attr = attrService.getAttrInfo(attrId);
        return R.ok().put("attr", attr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrVo attr){

		attrService.saveAttr(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attr:update")
    public R update(@RequestBody AttrVo attr){
		attrService.updateAttr(attr);

        return R.ok();
    }
    @PostMapping("/update/{spuId}")
    //@RequiresPermissions("product:attr:update")
    public R updateSpuAttr(@RequestBody List<ProductAttrValueEntity> attr,
                           @PathVariable Long spuId){
        productAttrValueService.updateSpuAttr(spuId,attr);

        return R.ok();
    }
    /**
     * 删除
     */
    @RequestMapping("/delete")
   // @RequiresPermissions("product:attr:delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
