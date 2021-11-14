package com.atguigu.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.product.entity.ProductAttrValueEntity;

import java.util.Map;

/**
 * spu属性值
 *
 * @author sunny
 * @email sunny@gmail.com
 * @date 2021-11-14 16:54:42
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

