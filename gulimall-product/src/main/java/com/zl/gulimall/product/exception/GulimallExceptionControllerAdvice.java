package com.zl.gulimall.product.exception;

import com.zl.common.exception.BizCodeEnum;
import com.zl.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhuLing
 * @date 2021/11/8 - 18:31
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.zl.gulimall.product.controller")
public class GulimallExceptionControllerAdvice {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e){
        log.error("数据校验错误{}，异常类型{}",e.getMessage(),e.getClass());
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> map = new HashMap<>();
        bindingResult.getFieldErrors().forEach(item->{
            map.put(item.getField(),item.getDefaultMessage());
        });
        return R.error(BizCodeEnum.VALID_EXCEPTION.getErrorCode(),BizCodeEnum.VALID_EXCEPTION.getErrorMessage())
                .put("data",map);

    }
    @ExceptionHandler(value = Throwable.class)
    public R handleAllException(Throwable throwable){
        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION.getErrorCode(),BizCodeEnum.UNKNOWN_EXCEPTION.getErrorMessage())
                .put("errorMessage",throwable.getMessage());
    }
}
