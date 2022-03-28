package com.atguigu.gulimall.auth.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class LoginController {

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 发送一个请求跳转到一个页面
     * SpringMVC viewcontroller 将请求和页面映射过来
     */



}
