package com.zl.gulimall.member.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.zl.common.exception.BizCodeEnum;
import com.zl.gulimall.member.exception.PhoneExistException;
import com.zl.gulimall.member.exception.UsernameExistException;
import com.zl.gulimall.member.feign.CouponFeignService;
import com.zl.gulimall.member.vo.MemberLoginVo;
import com.zl.gulimall.member.vo.MemberRegistVo;
import com.zl.gulimall.member.vo.SocialUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zl.gulimall.member.entity.MemberEntity;
import com.zl.gulimall.member.service.MemberService;
import com.zl.common.utils.PageUtils;
import com.zl.common.utils.R;



/**
 * 会员
 *
 * @author zhuling
 * @email 373598252@qq.com
 * @date 2021-11-05 16:40:12
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    /**
     * 列表
     */
    @Autowired
    CouponFeignService couponFeignService;
    @RequestMapping("/coupon")
    public R test(){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setUsername("zl");
        R membercoupons = couponFeignService.memberCoupon();
        return R.ok().put("member",memberEntity).put("coupons",membercoupons.get("coupons"));
    }
    @RequestMapping("/list")
    //@RequiresPermissions("member:member:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("member:member:info")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:member:save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("member:member:update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
   // @RequiresPermissions("member:member:delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
    @PostMapping("/regist")
    public R regist(@RequestBody MemberRegistVo vo){
        try {
            memberService.regist(vo);
        }catch (PhoneExistException e){
            return R.error(BizCodeEnum.PHONE_EXIST_EXCEPTION.getErrorCode(),BizCodeEnum.PHONE_EXIST_EXCEPTION.getErrorMessage());
        }catch (UsernameExistException e){
            return R.error(BizCodeEnum.USER_EXIST_EXCEPTION.getErrorCode(),BizCodeEnum.USER_EXIST_EXCEPTION.getErrorMessage());
        }

        return R.ok();
    }
    @PostMapping("/login")
    public R login(@RequestBody MemberLoginVo vo){
       MemberEntity memberEntity = memberService.login(vo);
       if (memberEntity==null){
            return R.error(BizCodeEnum.LOGINACCT_PASSWORD_INVALID_EXCEPTION.getErrorCode(),BizCodeEnum.LOGINACCT_PASSWORD_INVALID_EXCEPTION.getErrorMessage());
       }else{
           return R.ok().setData(memberEntity);
       }
    }
    @PostMapping("/oauth/login")
    public R oauthLogin(@RequestBody SocialUser socialUser){
        MemberEntity memberEntity=memberService.oauthLogin(socialUser);
        if (memberEntity!=null){
            return  R.ok().setData(memberEntity);
        }else {
            return  R.error(BizCodeEnum.LOGINACCT_PASSWORD_INVALID_EXCEPTION.getErrorCode(),BizCodeEnum.LOGINACCT_PASSWORD_INVALID_EXCEPTION.getErrorMessage());
        }
    }
}
