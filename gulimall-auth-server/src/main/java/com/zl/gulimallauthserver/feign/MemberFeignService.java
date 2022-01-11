package com.zl.gulimallauthserver.feign;

import com.zl.common.utils.R;
import com.zl.gulimallauthserver.vo.SocialUser;
import com.zl.gulimallauthserver.vo.UserLoginVo;
import com.zl.gulimallauthserver.vo.UserRegistVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ZhuLing
 * @date 2021/11/22 - 19:43
 */
@FeignClient("gulimall-member")
public interface MemberFeignService {
    @PostMapping("/member/member/regist")
    public R regist(@RequestBody UserRegistVo vo);
    @PostMapping("/member/member/login")
    public R login(@RequestBody UserLoginVo vo);
    @PostMapping("/member/member/oauth/login")
    public R oauthLogin(@RequestBody SocialUser socialUser);
}
