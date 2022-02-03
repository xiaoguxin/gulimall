package com.atguigu.gulimall.product.exception;

import com.atguigu.common.exception.BizCode;
import com.atguigu.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 集中处理所有异常
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.atguigu.gulimall.product.controller")
public class GulimallExceptionControllerAdvice {


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleVaildException(MethodArgumentNotValidException e) {
        log.error("数据校验出现问题{},异常类型：{}", e.getMessage(), e.getClass());
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> errorMap = new HashMap<>();
        //1、获取校验的错误结果
        bindingResult.getFieldErrors().forEach((item) -> {

            //获取错误的属性的名字
            String field = item.getField();
            //FieldError获取到错误提示
            String message = item.getDefaultMessage();
            errorMap.put(field, message);
        });
        return R.error(BizCode.VAILD_EXCEPTION.getCode(), BizCode.VAILD_EXCEPTION.getMsg()).put("data", errorMap);
    }

    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable) {

        return R.error(BizCode.UNKNOW_EXCEPTION.getCode(), BizCode.UNKNOW_EXCEPTION.getMsg());
    }
}
