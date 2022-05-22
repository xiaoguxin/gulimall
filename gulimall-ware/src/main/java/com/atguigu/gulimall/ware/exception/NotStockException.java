package com.atguigu.gulimall.ware.exception;

public class NotStockException extends RuntimeException{
    private Long skuId;

    public NotStockException(Long skuId){
        super("商品id:"+skuId+";没有足够的库存了");
    }

    public Long getSkuId(){
        return skuId;
    }

    public void setSkuId(Long skuId){
        this.skuId = skuId;
    }
}
