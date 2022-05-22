package com.atguigu.gulimall.ware.vo;

import lombok.Data;

@Data
public class LockStockResult {

    private long skuId;
    private Integer num;
    private Boolean locked;

}
