package com.atguigu.gulimall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Catelog2Vo {
    private String catalog1Id; //1级父分类id

    private List<Catelog3Vo> catalog3List; //三级子分类

    private String id; //二级分类id

    private String name; //二级分类名称

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static  class Catelog3Vo{
        private String catalog2Id;//父分类，2级分类id
        private String id;
        private String name;
    }
}
