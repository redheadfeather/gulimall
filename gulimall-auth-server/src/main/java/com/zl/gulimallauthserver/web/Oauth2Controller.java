package com.zl.gulimallauthserver.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zl.common.utils.R;
import com.zl.common.vo.MemberRespVo;
import com.zl.gulimallauthserver.feign.MemberFeignService;
import com.zl.gulimallauthserver.utils.HttpUtils;
import com.zl.gulimallauthserver.vo.SocialUser;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhuLing
 * @date 2021/11/22 - 22:32
 */
@Controller
public class Oauth2Controller {
    @Autowired
    MemberFeignService memberFeignService;
    @GetMapping("/oauth/gitee/success")
    public String gitee(@RequestParam String code, RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception {
      //  https://gitee.com/oauth/token?grant_type=authorization_code&code={code}&client_id={client_id}&redirect_uri={redirect_uri}&client_secret={client_secret}
        Map<String, String> rbody = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        rbody.put("grant_type","authorization_code");
        rbody.put("code",code);
        rbody.put("client_id","29e6d9f318235e89902f33e6ebbc4a2fceef110e8dfc31884bed037df5de8ba4");
        rbody.put("redirect_uri","http://auth.gulimall.com/oauth/gitee/success");
        rbody.put("client_secret","af9ae95e94303e9bcc3bf911b181cddf4552c6e149278761386c6f1697cfb95c");
        Map<String, String> header = new HashMap<>();
        HttpResponse post = HttpUtils.doPost("https://gitee.com", "/oauth/token", "post", header, null, rbody);
        if (post.getStatusLine().getStatusCode()==200){
            String s = EntityUtils.toString(post.getEntity());
            SocialUser socialUser = JSON.parseObject(s, SocialUser.class);
            Map<String, String> query = new HashMap<>();
            query.put("access_token",socialUser.getAccess_token());
            HttpResponse idget = HttpUtils.doGet("https://gitee.com", "/api/v5/user", "get", header, query);
            if (idget.getStatusLine().getStatusCode()==200){
                String s1 = EntityUtils.toString(idget.getEntity());
                Map<String, String> map = JSON.parseObject(s1, Map.class);
                System.out.println(map.toString());
                String id = String.valueOf(map.get("id"));
                String name = map.get("name");
                socialUser.setName(name);
                socialUser.setId(id);
                R r = memberFeignService.oauthLogin(socialUser);
                if (r.getCode()==0){
                    MemberRespVo memberRespVo = r.getData("data",new TypeReference<MemberRespVo>(){});
                    HttpSession session = request.getSession();
                    session.setAttribute("loginUser",memberRespVo);
                    return "redirect:http://gulimall.com";
                }else{
                    errors.put("msg",r.get("msg").toString());
                    redirectAttributes.addFlashAttribute("errors",errors);
                    return "redirect:http://auth.gulimall.com/login.html";
                }
            }else{
                return "redirect:http://auth.gulimall.com/login.html";
            }
        }else{
            return "redirect:http://auth.gulimall.com/login.html";
        }

    }
}
