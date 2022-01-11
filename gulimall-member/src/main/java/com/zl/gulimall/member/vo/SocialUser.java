package com.zl.gulimall.member.vo;

import lombok.Data;

/**
 * @author ZhuLing
 * @date 2021/11/23 - 0:01
 */
@Data
public class SocialUser {
    private String access_token;

    private String token_type;

    private long expires_in;

    private String refresh_token;

    private String scope;

    private long created_at;

    private String id;
    private String name;
}
