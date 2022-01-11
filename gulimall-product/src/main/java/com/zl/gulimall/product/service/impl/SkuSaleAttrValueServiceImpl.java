package com.zl.gulimall.product.service.impl;

import com.zl.gulimall.product.vo.SkuItemSaleAttrVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.common.utils.PageUtils;
import com.zl.common.utils.Query;

import com.zl.gulimall.product.dao.SkuSaleAttrValueDao;
import com.zl.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.zl.gulimall.product.service.SkuSaleAttrValueService;


@Service("skuSaleAttrValueService")
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueDao, SkuSaleAttrValueEntity> implements SkuSaleAttrValueService {
    @Override
    public List<SkuItemSaleAttrVo> getSkuItemSaleAttrVo(Long spuId) {
        return this.baseMapper.getSkuItemSaleAttrVo(spuId);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuSaleAttrValueEntity> page = this.page(
                new Query<SkuSaleAttrValueEntity>().getPage(params),
                new QueryWrapper<SkuSaleAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<SkuSaleAttrValueEntity> skuIdInfo(Long skuId) {
        List<SkuSaleAttrValueEntity> sku_id = this.baseMapper.selectList(new QueryWrapper<SkuSaleAttrValueEntity>().eq("sku_id", skuId));
        return sku_id;
    }

}