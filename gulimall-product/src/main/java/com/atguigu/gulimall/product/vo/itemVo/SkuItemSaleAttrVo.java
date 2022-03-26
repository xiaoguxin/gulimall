package com.atguigu.gulimall.product.vo.itemVo;

import lombok.Data;

import java.util.List;


@Data
public class SkuItemSaleAttrVo{
    private Long attrId;
    private String attrName;
    private List<AttrValueWithSkuIdVo> attrValues;
}