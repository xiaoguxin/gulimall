package com.atguigu.gulimall.order.interceptor;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class OrderPayedListener {

    //外网需要访问的地址(可以使用内网穿透)
    @PostMapping("/payed/notify")
    public String handleAlipayed(HttpServletRequest request){
        //只要我们收到了支付宝给我们异步的通知，告诉我们订单支付成功。返回success，支付宝就再也不通知
        Map<String, String[]> map = request.getParameterMap();
        System.out.println("支付宝通知到位了...数据："+map);
        return "success";
    }
}
