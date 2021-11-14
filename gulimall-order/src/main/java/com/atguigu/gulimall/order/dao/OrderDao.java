package com.atguigu.gulimall.order.dao;

import com.atguigu.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author sunny
 * @email sunny@gmail.com
 * @date 2021-11-14 19:33:14
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
