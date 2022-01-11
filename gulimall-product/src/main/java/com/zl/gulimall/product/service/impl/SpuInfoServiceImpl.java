package com.zl.gulimall.product.service.impl;

import com.mchange.v2.beans.BeansUtils;
import com.zl.common.constant.ProductConstant;
import com.zl.common.to.MemberPrice;
import com.zl.common.to.SkuHasStockVo;
import com.zl.common.to.SkuReductionTo;
import com.zl.common.to.SpuBoundTo;
import com.zl.common.to.es.SkuEsModel;
import com.zl.common.utils.R;
import com.zl.gulimall.product.entity.*;
import com.zl.gulimall.product.feign.CouponFeignService;
import com.zl.gulimall.product.feign.SearchFeignService;
import com.zl.gulimall.product.feign.WareFeignService;
import com.zl.gulimall.product.service.*;
import com.zl.gulimall.product.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.common.utils.PageUtils;
import com.zl.common.utils.Query;

import com.zl.gulimall.product.dao.SpuInfoDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {
    @Autowired
    SpuInfoDescService spuInfoDescService;
    @Autowired
    SpuImagesService spuImagesService;
    @Autowired
    AttrService attrService;
    @Autowired
    ProductAttrValueService productAttrValueService;
    @Autowired
    SkuInfoService skuInfoService;
    @Autowired
    SkuImagesService skuImagesService;
    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;
    @Autowired
    CouponFeignService couponFeignService;
    @Autowired
    BrandService brandService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    WareFeignService wareFeignService;
    @Autowired
    SearchFeignService searchFeignService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }
    @Transactional
    @Override
    public void saveSpuInfo(SpuSaveVo spuInfo) {
        //保存spu基本信息
        SpuInfoEntity infoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(spuInfo,infoEntity);
        infoEntity.setCreateTime(new Date());
        infoEntity.setUpdateTime(new Date());
        this.saveBaseSpuInfo(infoEntity);
        //保存spu的描述图片
        List<String> descipt = spuInfo.getDecript();
        SpuInfoDescEntity descEntity = new SpuInfoDescEntity();
        descEntity.setSpuId(infoEntity.getId());
        descEntity.setDecript(String.join(",",descipt));
        this.saveSpuInfoDesc(descEntity);
        //保存spu的图片集pms_spu_images
        List<String> images = spuInfo.getImages();
        spuImagesService.saveImages(infoEntity.getId(),images);
        //保存spu规格参数
        List<BaseAttrs> attrs = spuInfo.getBaseAttrs();
        List<ProductAttrValueEntity> collect = attrs.stream().map(attr -> {
            ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();
            productAttrValueEntity.setAttrId(attr.getAttrId());
            AttrEntity attrEntity = attrService.getById(attr.getAttrId());
            productAttrValueEntity.setAttrName(attrEntity.getAttrName());
            productAttrValueEntity.setQuickShow(attr.getShowDesc());
            productAttrValueEntity.setAttrValue(attr.getAttrValues());
            productAttrValueEntity.setSpuId(infoEntity.getId());
            return productAttrValueEntity;
        }).collect(Collectors.toList());
        productAttrValueService.saveBatch(collect);
        //保存spu的积分信息gulimall_sms->sms_spu_bounds
        Bounds bounds = spuInfo.getBounds();
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        BeanUtils.copyProperties(bounds,spuBoundTo);
        spuBoundTo.setSpuId(infoEntity.getId());
        R r = couponFeignService.saveSpuBounds(spuBoundTo);
        //保存spu对应的sku信息
        List<Skus> skus = spuInfo.getSkus();
        if (skus!=null&&skus.size()!=0){
            skus.forEach(item->{
                String defaultImg="";
                for (Images image:item.getImages()) {
                    if (image.getDefaultImg()==1) {
                        defaultImg=image.getImgUrl();
                    }
                }
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(item,skuInfoEntity);
                skuInfoEntity.setBrandId(infoEntity.getBrandId());
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSpuId(infoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(defaultImg);
                //sku基本信息：pms_sku_info
                skuInfoService.saveSkuInfo(skuInfoEntity);

                List<SkuImagesEntity> collect1 = item.getImages().stream().map(image -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setSkuId(skuInfoEntity.getSkuId());
                    skuImagesEntity.setImgUrl(image.getImgUrl());
                    skuImagesEntity.setDefaultImg(image.getDefaultImg());
                    return skuImagesEntity;
                }).filter(skuImage->{
                    return !StringUtils.isEmpty(skuImage.getImgUrl());
                }).collect(Collectors.toList());
                //sku图片信息:pms_sku_images
                skuImagesService.saveBatch(collect1);
                List<Attr> attr = item.getAttr();
                List<SkuSaleAttrValueEntity> collect2 = attr.stream().map(attritem -> {
                    SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(attritem, skuSaleAttrValueEntity);
                    skuSaleAttrValueEntity.setSkuId(skuInfoEntity.getSkuId());
                    return skuSaleAttrValueEntity;
                }).collect(Collectors.toList());
                //sku销售属性信息:pms_sku_sale_attr_value
                skuSaleAttrValueService.saveBatch(collect2);
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(item,skuReductionTo);
                skuReductionTo.setSkuId(infoEntity.getId());
                List<MemberPrice> memberPriceToList = item.getMemberPrice().stream().map(memberPrice -> {
                    MemberPrice memberPriceTo = new MemberPrice();
                    BeanUtils.copyProperties(memberPrice, memberPriceTo);
                    return memberPriceTo;
                }).collect(Collectors.toList());
                skuReductionTo.setMemberPrice(memberPriceToList);
                // sku优惠满减信息gulimall_sms->sms_sku_ladder\sms_sku_full_reduction
                R r1=couponFeignService.saveSkuReduction(skuReductionTo);
            });
        }
    }

    @Override
    public void saveBaseSpuInfo(SpuInfoEntity infoEntity) {
        this.baseMapper.insert(infoEntity);
    }

    @Override
    public void saveSpuInfoDesc(SpuInfoDescEntity descEntity) {
        spuInfoDescService.save(descEntity);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SpuInfoEntity> spuInfoEntityQueryWrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)){
            spuInfoEntityQueryWrapper.eq("id",key).or().like("spu_name",key);
        }
        String brandId = (String) params.get("brandId");
        if (!StringUtils.isEmpty(brandId)){
            spuInfoEntityQueryWrapper.eq("brand_id",brandId);
        }
        String status = (String) params.get("status");
        if (!StringUtils.isEmpty(status)){
            spuInfoEntityQueryWrapper.eq("publish_status",status);
        }
        String catelogId = (String) params.get("catelogId");
        if (!StringUtils.isEmpty(catelogId)){
            spuInfoEntityQueryWrapper.eq("catelog_id",catelogId);
        }
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                spuInfoEntityQueryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void up(Long spuId) {
        List<SkuEsModel> uoProduct = new ArrayList<>();

        List<SkuInfoEntity> spu_id = skuInfoService.list(new QueryWrapper<SkuInfoEntity>().eq("spu_id", spuId));
        List<Long> skuIdList = spu_id.stream().map(SkuInfoEntity::getSkuId).collect(Collectors.toList());
        BrandEntity brand = brandService.getById(spu_id.get(0).getBrandId());
        CategoryEntity byId = categoryService.getById(spu_id.get(0).getCatalogId());
        List<ProductAttrValueEntity> productAttrValueEntities = productAttrValueService.baseAttrListForSpu(spuId);
        List<Long> attrIds = productAttrValueEntities.stream().map(attr -> {
            return attr.getAttrId();
        }).collect(Collectors.toList());
        List<AttrEntity> attrEntities = attrService.listByIds(attrIds);
        List<Long> collect1 = attrEntities.stream().filter(item -> {
            return item.getSearchType() == 1;
        }).map(item -> {
            return item.getAttrId();
        }).collect(Collectors.toList());
        Set<Long> showSet = new HashSet<>(collect1);
        List<SkuEsModel.Attrs> attrs1 = productAttrValueEntities.stream().filter(item -> {
            return showSet.contains(item.getAttrId());
        }).map(item -> {
            SkuEsModel.Attrs attrs = new SkuEsModel.Attrs();
            BeanUtils.copyProperties(item, attrs);
            return attrs;
        }).collect(Collectors.toList());

        Map<Long, Boolean> stockMap = null;
        try {
            List<SkuHasStockVo> skusHasStock = wareFeignService.getSkusHasStock(skuIdList);
            stockMap = skusHasStock.stream().collect(Collectors.toMap(item -> item.getSkuId(), item -> item.getHasStock()));
        } catch (Exception e) {
            log.error("远程调用异常");
            e.printStackTrace();
        }
        Map<Long, Boolean> finalStockMap = stockMap;
        List<SkuEsModel> collect = spu_id.stream().map(sku -> {
            SkuEsModel skuEsModel = new SkuEsModel();
            BeanUtils.copyProperties(sku, skuEsModel);
            skuEsModel.setSkuPrice(sku.getPrice());
            skuEsModel.setSkuImg(sku.getSkuDefaultImg());
            skuEsModel.setBrandName(brand.getName());
            skuEsModel.setBrandImg(brand.getLogo());
            skuEsModel.setCatalogName(byId.getName());
            skuEsModel.setHotScore(0L);
            skuEsModel.setAttrs(attrs1);
            if (finalStockMap==null||finalStockMap.size()==0){
                skuEsModel.setHasStock(false);
            }else {
                skuEsModel.setHasStock(finalStockMap.get(sku.getSkuId()));
            }
            return skuEsModel;
        }).collect(Collectors.toList());
        R r = searchFeignService.productStatusUp(collect);
        if ( r.get("msg").equals("success")){
            baseMapper.updateSpuStatus(spuId, ProductConstant.StatusEnum.SPU_UP.getCode());
        }else{
            //todo 接口幂等性，失败重试

        }
    }

    @Override
    public SpuInfoEntity getSpuInfoBySkuId(Long skuId) {
        SkuInfoEntity byId = skuInfoService.getById(skuId);
        return this.baseMapper.selectById(byId.getSpuId());
    }

}