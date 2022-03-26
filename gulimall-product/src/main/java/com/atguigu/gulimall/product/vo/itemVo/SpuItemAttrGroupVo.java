package com.atguigu.gulimall.product.vo.itemVo;

import com.atguigu.gulimall.product.vo.Attr;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class SpuItemAttrGroupVo {
    private String groupName;
    private List<Attr> attrs;
}