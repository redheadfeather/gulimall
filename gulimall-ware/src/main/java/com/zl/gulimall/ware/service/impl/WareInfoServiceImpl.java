package com.zl.gulimall.ware.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.zl.common.utils.R;
import com.zl.gulimall.ware.feign.MemberFeignService;
import com.zl.gulimall.ware.vo.FareVo;
import com.zl.gulimall.ware.vo.MemberAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.common.utils.PageUtils;
import com.zl.common.utils.Query;

import com.zl.gulimall.ware.dao.WareInfoDao;
import com.zl.gulimall.ware.entity.WareInfoEntity;
import com.zl.gulimall.ware.service.WareInfoService;
import org.springframework.util.StringUtils;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {
    @Autowired
    MemberFeignService memberFeignService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareInfoEntity> wareInfoEntityQueryWrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.hasText(key)){
            wareInfoEntityQueryWrapper.eq("id",key).or().like("name",key);
        }
        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),
                wareInfoEntityQueryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public FareVo getFare(Long addrId) {
        FareVo fareVo = new FareVo();
        R info = memberFeignService.info(addrId);
        MemberAddressVo memberReceiveAddress = info.getData("memberReceiveAddress", new TypeReference<MemberAddressVo>() {
        });
        if (memberReceiveAddress!=null){
            String phone = memberReceiveAddress.getPhone();
            String substring = phone.substring(phone.length() - 1, phone.length());
            fareVo.setFare(new BigDecimal(substring));
            fareVo.setAddress(memberReceiveAddress);
            return fareVo;
        }
        return null;
    }

}