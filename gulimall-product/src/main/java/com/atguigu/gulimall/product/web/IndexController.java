package com.atguigu.gulimall.product.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({"/","/index.html"})
    public String indexPage(){


        //视图解析器进行拼串
        //classpath:/templates/+返回值+.html
        return "index";
    }
}
