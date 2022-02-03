package com.atguigu.gulimall.product.exception;

import com.atguigu.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 集中处理所有异常
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.atguigu.gulimall.product.controller")
public class GulimallExceptionControllerAdvice {


    @ExceptionHandler(value = Exception.class)
    public R handleVaildException(Exception e){
        log.error("数据校验出现问题{},异常类型：{}",e.getMessage(),e.getClass());
        return R.error();
    }
}
