package com.zl.gulimall.member.dao;

import com.zl.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author zhuling
 * @email 373598252@qq.com
 * @date 2021-11-05 16:40:12
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
