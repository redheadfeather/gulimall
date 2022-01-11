package com.zl.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.common.utils.PageUtils;
import com.zl.gulimall.product.entity.AttrEntity;
import com.zl.gulimall.product.vo.AttrGroupRelationVo;
import com.zl.gulimall.product.vo.AttrRespVo;
import com.zl.gulimall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author zhuling
 * @email 373598252@qq.com
 * @date 2021-11-05 15:07:17
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String attrtype);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);


    List<AttrEntity> getRelationAttr(Long attrgroupId);

    void deleteRelation(AttrGroupRelationVo[] attrGroupRelationVo);

    PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId);
}

