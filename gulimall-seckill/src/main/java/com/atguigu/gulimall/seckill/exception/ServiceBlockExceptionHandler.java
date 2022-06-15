package com.atguigu.gulimall.seckill.exception;


import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.atguigu.common.exception.BizCode;
import com.atguigu.common.utils.R;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class ServiceBlockExceptionHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        R error = R.error(BizCode.TO_MANY_REQUEST.getCode(), BizCode.TO_MANY_REQUEST.getMsg());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter().write(JSON.toJSONString(error));
    }
}
