package com.atguigu.gulimall.auth.controller;


import com.atguigu.gulimall.auth.util.DysmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
public class LoginController {

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 发送一个请求跳转到一个页面
     * SpringMVC viewcontroller 将请求和页面映射过来
     */

    /*@GetMapping("/login.html")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/reg.html")
    public String regPage(){
        return "reg";
    }*/
    @ResponseBody
    @GetMapping("/getCode")
    public String  getCode(String userTel){
        boolean matches = userTel.matches("^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$");
        if(!matches){
            return  "手机号码有误";
        }
        String code = DysmsUtil.getCode();
        redisTemplate.opsForValue().set(userTel,code,300, TimeUnit.SECONDS);// redis存储
        String  jsonCode  = "{'code':'"+code+"'}";
        DysmsUtil.sendMsg(userTel,DysmsUtil.getMsgCode(),jsonCode);
        log.info("手机号:"+userTel +"获取验证码成功:["+code+"]" );
        return  "获取验证码成功";
    }

}
