package com.zl.gulimallauthserver.web;

import com.alibaba.fastjson.TypeReference;
import com.zl.common.constant.AuthServerConstant;
import com.zl.common.exception.BizCodeEnum;
import com.zl.common.utils.R;
import com.zl.common.vo.MemberRespVo;
import com.zl.gulimallauthserver.feign.MemberFeignService;
import com.zl.gulimallauthserver.feign.SmsFeignService;
import com.zl.gulimallauthserver.vo.UserLoginVo;
import com.zl.gulimallauthserver.vo.UserRegistVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhuLing
 * @date 2021/11/21 - 21:04
 */
@Controller
public class WebController {
//    @RequestMapping("/{index}")
//    public String index(@PathVariable String index){
//        return index;
//    }
    @Autowired
    SmsFeignService smsFeignService;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    MemberFeignService memberFeignService;
    @ResponseBody
    @GetMapping("/sms/sendcode")
    public R sendCode(@RequestParam String phone){
        String redisCode = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);
        if (!StringUtils.isEmpty(redisCode)){
            long time = Long.parseLong(redisCode.split("_")[1]);
            if (System.currentTimeMillis()-time<60000){
                return R.error(BizCodeEnum.VALID_SMS_CODE_EXCEPTION.getErrorCode(),BizCodeEnum.VALID_SMS_CODE_EXCEPTION.getErrorMessage());
            }
        }
        String code = UUID.randomUUID().toString().substring(0,5);
        smsFeignService.sendCode(phone,code);
        code=code+"_"+System.currentTimeMillis();
        redisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX+phone,code,60, TimeUnit.SECONDS);
        return R.ok();

    }

    @PostMapping("/regist")
    public String regist(@Valid UserRegistVo vo, BindingResult result, RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            List<FieldError> fieldErrors = result.getFieldErrors();
           fieldErrors.forEach(fieldError -> {
               errors.put(fieldError.getField(),fieldError.getDefaultMessage());
           });
            redirectAttributes.addFlashAttribute("errors",errors);
            return "redirect:http://auth.gulimall.com/reg.html";
        }
        String code = vo.getCode();
        String s = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
        if (!StringUtils.isEmpty(s)){
            if (s.split("_")[0].equals(code)){
                redisTemplate.delete(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
                R regist = memberFeignService.regist(vo);
                if (regist.getCode()==0){
                    return "redirect:http://auth.gulimall.com/login.html";
                }else{
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("msg",regist.getData("msg",new TypeReference<String>(){}));
                    return "redirect:http://auth.gulimall.com/reg.html";
                }
            }else{
                Map<String, String> codeMap = new HashMap<>();
                codeMap.put("code","验证码错误");
                redirectAttributes.addFlashAttribute("errors",codeMap);
                return "redirect:http://auth.gulimall.com/reg.html";
            }
        }else {
            Map<String, String> codeMap = new HashMap<>();
            codeMap.put("code","验证码错误");
            redirectAttributes.addFlashAttribute("errors",codeMap);
            return "redirect:http://auth.gulimall.com/reg.html";
        }
    }
    @PostMapping("/login")
    public String login(UserLoginVo vo, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest){
        R login = memberFeignService.login(vo);
        if (login.getCode()==0){
            MemberRespVo data = login.getData("data", new TypeReference<MemberRespVo>() {
            });
            data.setNickname(data.getUsername());
           httpServletRequest.getSession().setAttribute("loginUser",data);
            return "redirect:http://gulimall.com";
        }else{
            Map<String, String> errors = new HashMap<>();
            errors.put("msg",login.getData("msg",new TypeReference<String>(){}));
            redirectAttributes.addFlashAttribute("errors",errors);
            return "redirect:http://auth.gulimall.com/login.html";
        }

    }
    @GetMapping("/login.html")
    public String doLogin(HttpSession session){
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser==null){
            return "login";
        }else{
            return "redirect:http://gulimall.com";
        }
    }

}
