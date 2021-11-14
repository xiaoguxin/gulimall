package com.atguigu.gulimall.ware.dao;

import com.atguigu.gulimall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author sunny
 * @email sunny@gmail.com
 * @date 2021-11-14 19:35:30
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {
	
}
