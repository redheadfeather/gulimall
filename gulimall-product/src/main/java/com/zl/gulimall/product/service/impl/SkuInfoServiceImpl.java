package com.zl.gulimall.product.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.zl.common.utils.R;
import com.zl.gulimall.product.entity.SkuImagesEntity;
import com.zl.gulimall.product.feign.SeckillFeignService;
import com.zl.gulimall.product.service.*;
import com.zl.gulimall.product.vo.SeckillSkuVo;
import com.zl.gulimall.product.vo.SkuItemSaleAttrVo;
import com.zl.gulimall.product.vo.SkuItemVO;
import com.zl.gulimall.product.vo.SpuItemAttrGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.common.utils.PageUtils;
import com.zl.common.utils.Query;

import com.zl.gulimall.product.dao.SkuInfoDao;
import com.zl.gulimall.product.entity.SkuInfoEntity;
import org.springframework.util.StringUtils;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {
    @Autowired
    SkuImagesService skuImagesService;
    @Autowired
    SpuInfoDescService spuInfoDescService;
    @Autowired
    AttrGroupService attrGroupService;
    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;
    @Autowired
    ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    SeckillFeignService seckillFeignService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
        this.baseMapper.insert(skuInfoEntity);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SkuInfoEntity> skuInfoEntityQueryWrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)){
            skuInfoEntityQueryWrapper.eq("sku_id",key).or().like("sku_name",key);
        }
        String catalogId = (String) params.get("catalogId");
        if(!StringUtils.isEmpty(catalogId)){
            skuInfoEntityQueryWrapper.eq("catalog_id",catalogId);
        }
        String brandId = (String) params.get("brandId");
        if(!StringUtils.isEmpty(brandId)){
            skuInfoEntityQueryWrapper.eq("brand_id",brandId);
        }
        String min = (String) params.get("min");
        if(!StringUtils.isEmpty(min)&&!"0".equalsIgnoreCase(min)){
            skuInfoEntityQueryWrapper.ge("price",min);
        }
        String max = (String) params.get("max");
        if(!StringUtils.isEmpty(max)&&!"0".equalsIgnoreCase(max)){
            skuInfoEntityQueryWrapper.le("price",max);
        }
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                skuInfoEntityQueryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public SkuItemVO item(Long skuId) {
        SkuItemVO skuItemVO = new SkuItemVO();
        CompletableFuture<SkuInfoEntity> infoFuture = CompletableFuture.supplyAsync(()->{
            SkuInfoEntity info = getById(skuId);
            skuItemVO.setInfo(info);
            return info;
        },threadPoolExecutor);

        CompletableFuture imageFuture = CompletableFuture.runAsync(()->{
            List<SkuImagesEntity> images = skuImagesService.getImagesBySkuId(skuId);
            skuItemVO.setImages(images);
        },threadPoolExecutor);

        CompletableFuture<Void> descFuture = infoFuture.thenAcceptAsync(res -> {
            skuItemVO.setDesc(spuInfoDescService.getById(res.getSpuId()));
        }, threadPoolExecutor);

        CompletableFuture<Void> spuItemAttrGroupVoFuture = infoFuture.thenAcceptAsync(res -> {
            List<SpuItemAttrGroupVo> groupVos = attrGroupService.getAttrGroupWithAttrsBySpuId(res.getSpuId(), res.getCatalogId());
            skuItemVO.setGroupAttrs(groupVos);
        }, threadPoolExecutor);

        CompletableFuture<Void> skuItemSaleAttrVoFuture = infoFuture.thenAcceptAsync(res -> {
            List<SkuItemSaleAttrVo> skuItemSaleAttrVoList = skuSaleAttrValueService.getSkuItemSaleAttrVo(res.getSpuId());
            skuItemVO.setSaleAttr(skuItemSaleAttrVoList);
        }, threadPoolExecutor);
        CompletableFuture<Void> seckillFuture = CompletableFuture.runAsync(() -> {
            R skuSeckilInfo = seckillFeignService.getSkuSeckilInfo(skuId);
            if (skuSeckilInfo.getCode() == 0) {
                SeckillSkuVo data = skuSeckilInfo.getData(new TypeReference<SeckillSkuVo>() {
                });
                skuItemVO.setSeckillSkuVo(data);
            }
        }, threadPoolExecutor);

        CompletableFuture.allOf(imageFuture,descFuture,spuItemAttrGroupVoFuture,skuItemSaleAttrVoFuture,seckillFuture).join();

        return skuItemVO;
    }

}