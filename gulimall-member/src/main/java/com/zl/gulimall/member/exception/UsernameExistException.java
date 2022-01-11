package com.zl.gulimall.member.exception;

/**
 * @author ZhuLing
 * @date 2021/11/22 - 17:28
 */
public class UsernameExistException extends RuntimeException{
    public UsernameExistException(){
        super("用户名已存在");
    }
}
