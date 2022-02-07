package com.atguigu.gulimall.product.vo;

import lombok.Data;

@Data
public class AttrRespVo extends AttrVo{
    private String catelogName;//所属分类名

    private String groupName;//所属分组名

    private Long[] catelogPath;

}
