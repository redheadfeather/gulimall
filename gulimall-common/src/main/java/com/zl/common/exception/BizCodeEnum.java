package com.zl.common.exception;

/**
 * @author ZhuLing
 * @date 2021/11/8 - 18:50
 */
public enum BizCodeEnum {
    VALID_EXCEPTION(10001,"数据校验异常"),
    UNKNOWN_EXCEPTION(10000,"系统未知异常"),
    TO_MANY_REQUEST(10003,"请求流量过大，请稍后再试"),
    VALID_SMS_CODE_EXCEPTION(10002,"验证码获取频率太高，请稍候再试"),
    PRODUCT_UP_EXCEPTION(11000,"商品上架异常"),
    USER_EXIST_EXCEPTION(15001,"用户已存在"),
    PHONE_EXIST_EXCEPTION(15002,"手机号已存在"),
    LOGINACCT_PASSWORD_INVALID_EXCEPTION(15003,"用户名或密码错误"),
    NO_STOCK_EXCEPTION(21000,"没有库存");

    private String ErrorMessage;
    private Integer ErrorCode;
    BizCodeEnum(Integer ErrorCode,String ErrorMessage){
        this.ErrorCode=ErrorCode;
        this.ErrorMessage=ErrorMessage;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public Integer getErrorCode() {
        return ErrorCode;
    }
}
