package com.atguigu.gulimall.product.dao;

import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author sunny
 * @email
 * @date 2021-11-14 16:54:42
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
