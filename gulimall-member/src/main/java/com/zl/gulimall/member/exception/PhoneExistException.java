package com.zl.gulimall.member.exception;

/**
 * @author ZhuLing
 * @date 2021/11/22 - 17:29
 */
public class PhoneExistException extends RuntimeException{
    public PhoneExistException(){
        super("手机号已存在");
    }
}
