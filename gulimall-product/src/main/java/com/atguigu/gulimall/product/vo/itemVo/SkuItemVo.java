package com.atguigu.gulimall.product.vo.itemVo;

import com.atguigu.gulimall.product.entity.SkuImagesEntity;
import com.atguigu.gulimall.product.entity.SkuInfoEntity;
import com.atguigu.gulimall.product.entity.SpuInfoDescEntity;
import com.atguigu.gulimall.product.vo.SeckillInfoVo;
import lombok.Data;

import java.util.List;

@Data
public class SkuItemVo {
    //1、sku基本信息获取 pms_sku_info
    SkuInfoEntity info;

    Boolean hasStock = true ;

    //2、sku的图片信息   pms_sku_images
    List<SkuImagesEntity> images;

    //3、获取spu的销售属性组合。
    List<SkuItemSaleAttrVo> saleAttr;


    //4、获取spu的介绍
    SpuInfoDescEntity desc;

    //5、获取spu的规格参数信息
    List<SpuItemAttrGroupVo> groupAttrs;

    //6、当前商品的秒杀优惠信息
    SeckillInfoVo seckillInfo;
}
