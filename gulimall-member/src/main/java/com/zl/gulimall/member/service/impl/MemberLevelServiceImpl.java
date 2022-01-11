package com.zl.gulimall.member.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.common.utils.PageUtils;
import com.zl.common.utils.Query;

import com.zl.gulimall.member.dao.MemberLevelDao;
import com.zl.gulimall.member.entity.MemberLevelEntity;
import com.zl.gulimall.member.service.MemberLevelService;


@Service("memberLevelService")
public class MemberLevelServiceImpl extends ServiceImpl<MemberLevelDao, MemberLevelEntity> implements MemberLevelService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberLevelEntity> page = this.page(
                new Query<MemberLevelEntity>().getPage(params),
                new QueryWrapper<MemberLevelEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public MemberLevelEntity getDefaultLevel() {
        return this.baseMapper.selectOne(new QueryWrapper<MemberLevelEntity>().eq("default_status",1));
    }

}