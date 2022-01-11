package com.zl.gulimall.member.service.impl;

import com.zl.gulimall.member.entity.MemberLevelEntity;
import com.zl.gulimall.member.exception.PhoneExistException;
import com.zl.gulimall.member.exception.UsernameExistException;
import com.zl.gulimall.member.service.MemberLevelService;
import com.zl.gulimall.member.vo.MemberLoginVo;
import com.zl.gulimall.member.vo.MemberRegistVo;
import com.zl.gulimall.member.vo.SocialUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.common.utils.PageUtils;
import com.zl.common.utils.Query;

import com.zl.gulimall.member.dao.MemberDao;
import com.zl.gulimall.member.entity.MemberEntity;
import com.zl.gulimall.member.service.MemberService;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {
    @Autowired
    MemberLevelService memberLevelService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void regist(MemberRegistVo vo) {
        MemberEntity memberEntity = new MemberEntity();
        MemberLevelEntity levelEntity = memberLevelService.getDefaultLevel();
        memberEntity.setLevelId(levelEntity.getId());
        checkUsernameUnique(vo.getUserName());
        memberEntity.setUsername(vo.getUserName());
        checkPhoneUnique(vo.getPhone());
        memberEntity.setMobile(vo.getPhone());
        memberEntity.setCreateTime(new Date());
        memberEntity.setNickname(vo.getUserName());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(vo.getPassword());
        memberEntity.setPassword(encode);
        this.baseMapper.insert(memberEntity);
    }

    @Override
    public void checkPhoneUnique(String phone)throws PhoneExistException {
        Integer mobile = this.baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("mobile", phone));
        if (mobile>0){
            throw  new PhoneExistException();
        }
    }

    @Override
    public void checkUsernameUnique(String userName)throws UsernameExistException {
        Integer usernameNum = this.baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("username", userName));
        if (usernameNum>0){
            throw  new UsernameExistException();
        }
    }

    @Override
    public MemberEntity login(MemberLoginVo vo) {
        MemberEntity memberEntity = this.baseMapper.selectOne(new QueryWrapper<MemberEntity>().eq("username", vo.getLoginacct()).or().eq("mobile", vo.getLoginacct()));
        if (memberEntity==null){
            return null;
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if(bCryptPasswordEncoder.matches(vo.getPassword(),memberEntity.getPassword())){
            return memberEntity;
        }else {
            return null;
        }

    }

    @Override
    public MemberEntity oauthLogin(SocialUser socialUser) {
        MemberEntity memberEntity = this.baseMapper.selectOne(new QueryWrapper<MemberEntity>().eq("social_id", socialUser.getId()));
        if (memberEntity!=null){
            memberEntity.setAccessToken(socialUser.getAccess_token());
            memberEntity.setExpiresIn(String.valueOf(socialUser.getExpires_in()));
            memberEntity.setSocialId(socialUser.getId());
            memberEntity.setNickname(socialUser.getName());
            this.baseMapper.updateById(memberEntity);
            return memberEntity;
        }else{
            MemberEntity member = new MemberEntity();
            member.setAccessToken(socialUser.getAccess_token());
            member.setExpiresIn(String.valueOf(socialUser.getExpires_in()));
            member.setSocialId(socialUser.getId());
            member.setNickname(socialUser.getName());
            this.baseMapper.insert(member);
            return member;
        }

    }

}