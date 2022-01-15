package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> findListTree() {
        //1.查询出所有分类
        List<CategoryEntity> categoryList = baseMapper.selectList(null);

        //1.1查询出所有的一级分类
        List<CategoryEntity> categoryParentList = categoryList.stream()
                .filter(c -> c.getParentCid().equals(0L))
                .map((c) -> {
                    c.setChildren(getChildrenList(c, categoryList));
                    return c;
                }).sorted((c1, c2) -> {
                    return (c1.getSort() == null ? 0 : c1.getSort()) - (c2.getSort() == null ? 0 : c2.getSort());
                }).collect(Collectors.toList());


        return categoryParentList;
    }

    private List<CategoryEntity> getChildrenList(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> children = all.stream().
                filter(c -> root.getCatId().equals(c.getParentCid()))
                .map((c) -> {
                    c.setChildren(getChildrenList(c, all));
                    return c;
                }).sorted((c1, c2) -> {
                    return (c1.getSort() == null ? 0 : c1.getSort()) - (c2.getSort() == null ? 0 : c2.getSort());
                }).collect(Collectors.toList());
        return children;
    }
}