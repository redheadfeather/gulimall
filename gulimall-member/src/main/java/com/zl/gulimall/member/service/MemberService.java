package com.zl.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.common.utils.PageUtils;
import com.zl.gulimall.member.entity.MemberEntity;
import com.zl.gulimall.member.exception.PhoneExistException;
import com.zl.gulimall.member.exception.UsernameExistException;
import com.zl.gulimall.member.vo.MemberLoginVo;
import com.zl.gulimall.member.vo.MemberRegistVo;
import com.zl.gulimall.member.vo.SocialUser;

import java.util.Map;

/**
 * 会员
 *
 * @author zhuling
 * @email 373598252@qq.com
 * @date 2021-11-05 16:40:12
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void regist(MemberRegistVo vo);

    void checkPhoneUnique(String phone) throws PhoneExistException;
    void checkUsernameUnique(String userName) throws UsernameExistException;

    MemberEntity login(MemberLoginVo vo);

    MemberEntity oauthLogin(SocialUser socialUser);
}

