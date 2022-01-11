package com.zl.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.common.utils.PageUtils;
import com.zl.gulimall.ware.entity.PurchaseEntity;
import com.zl.gulimall.ware.vo.MergeVo;
import com.zl.gulimall.ware.vo.PurchaseFinishVo;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author zhuling
 * @email 373598252@qq.com
 * @date 2021-11-05 16:36:29
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageUnreceiveList(Map<String, Object> params);

    void mergePurchase(MergeVo mergeVo);

    void reiceved(List<Long> ids);

    void done(PurchaseFinishVo ids);
}

