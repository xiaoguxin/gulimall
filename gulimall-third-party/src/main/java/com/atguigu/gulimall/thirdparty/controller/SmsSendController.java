package com.atguigu.gulimall.thirdparty.controller;


import com.atguigu.common.utils.R;
import com.atguigu.gulimall.thirdparty.util.DysmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
public class SmsSendController {

    @Autowired
    private DysmsUtil dysmsUtil;

    /**
     * 提供给别的服务进行调用
     * @param phone
     * @param code
     * @return
     */
    @GetMapping("/sendcode")
    public R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code){
        String  jsonCode  = "{'code':'"+code+"'}";
        DysmsUtil.sendMsg(phone,DysmsUtil.getMsgCode(),jsonCode);
        return R.ok();
    }

}
