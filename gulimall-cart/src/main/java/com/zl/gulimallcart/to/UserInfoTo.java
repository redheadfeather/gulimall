package com.zl.gulimallcart.to;

import lombok.Data;

/**
 * @author ZhuLing
 * @date 2021/11/24 - 21:23
 */
@Data
public class UserInfoTo {
    private Long userId;
    private String userKey;
    private Boolean tempUser = false;
}
