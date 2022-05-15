package com.atguigu.gulimall.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

//订单确认页需要用的数据
@Data
public class OrderConfirmVo {

    //收货地址，ums_member_receive_address表
    List<MemberAddressVo> address;

    //所有选中的购物项
    List<OrderItemVo> items;

    //发票记录....

    //优惠券信息...
    Integer integration;

    BigDecimal total;//订单总额

    BigDecimal payPrice;//应付价格
}
