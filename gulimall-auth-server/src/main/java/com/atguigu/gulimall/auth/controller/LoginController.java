package com.atguigu.gulimall.auth.controller;


import com.atguigu.common.constant.AuthServerConstant;
import com.atguigu.common.exception.BizCode;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.auth.feign.ThirdPartFeignService;
import com.atguigu.gulimall.auth.vo.UserRegistVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class LoginController {

    @Autowired
    ThirdPartFeignService thirdPartFeignService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @ResponseBody
    @GetMapping("/sms/sendcode")
    public R sendCode(@RequestParam("phone")String phone){

        //0、号码格式校验
        boolean matches = phone.matches("^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$");
        if(!matches){
            return  R.error(BizCode.SMS_PHONE_FORMAT.getCode(),BizCode.SMS_PHONE_FORMAT.getMsg());
        }

        //TODO 1、接口防刷。
        String redisCode = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);
        if(StringUtils.isNotBlank(redisCode)){
            long l = Long.parseLong(redisCode.split("_")[1]);
            if (System.currentTimeMillis()-l<60000){
                //60秒内不能再发
                return R.error(BizCode.SMS_CODE_EXCEPTION.getCode(),BizCode.SMS_CODE_EXCEPTION.getMsg());
            }
        }

        //2、验证码的再次校验。redis  sms:code:号码 -> 12345
        String code =  (int) ((Math.random() * 9 + 1) * 100000) +"";
        String valueCode = code+ "_"+System.currentTimeMillis();
        //redis缓存验证码，防止同一个phone在60秒内再次发送验证码
        redisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX+phone,valueCode,5, TimeUnit.MINUTES);
        thirdPartFeignService.sendCode(phone,code);

        return R.ok();
    }

    /**
     * //TODO 重定向携带数据，利用session原理。
     *  将数据放在sesiion中。只要跳到下一个页面取出这个数据以后，
     *  session里面的数据就会删掉
     *  //TODO 1、分布下的session问题
     * RedirectAttributes redirectAttributes:模拟重定向携带数据
     *
     * @param vo
     * @param result
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/regist")
    public String regist(@Valid UserRegistVo vo, BindingResult result,
                         RedirectAttributes redirectAttributes){
        if (result.hasErrors()){

            Map<String, String> errors = result.getFieldErrors().stream().collect(
              Collectors.toMap(FieldError::getField,FieldError::getDefaultMessage)
            );

            //model.addAttribute("errors",errors);
            redirectAttributes.addFlashAttribute("errors",errors);
            //Requset method 'POST' not supported
            //用户注册->/regist[post]----->转发/reg.html(路径映射默认都是get方式访问的)


            //校验出错，转发到注册页
            return "redirect:http://auth.mall.com/reg.html";
        }

        //真正注册。调用远程服务进行注册

        //注册成功回到首页，回到登录页
        return "redirect:/login.html";
    }

}
