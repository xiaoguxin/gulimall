package com.atguigu.gulimall.search.controller;

import com.atguigu.common.exception.BizCode;
import com.atguigu.common.to.es.SkuEsModel;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/search/save")
@RestController
public class ElasticSaveController {

    @Autowired
    ProductSaveService productSaveService;

    //上架商品
    @PostMapping("/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels){
        Boolean b =false;
        try{
            b = productSaveService.productStatusUp(skuEsModels);
        }catch (Exception e){
            log.error("ElasticSaveController商品上架错误:{}",e);
            return R.error(BizCode.PRODUCT_UP_EXCEPTION.getCode(),BizCode.PRODUCT_UP_EXCEPTION.getMsg());
        }

        if(b){
            //成功
            return R.ok();
        }else{
            return R.error(BizCode.PRODUCT_UP_EXCEPTION.getCode(),BizCode.PRODUCT_UP_EXCEPTION.getMsg());
        }

    }
}
