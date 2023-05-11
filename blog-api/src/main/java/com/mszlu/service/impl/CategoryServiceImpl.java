package com.mszlu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.dao.mapper.CategoryMapper;
import com.mszlu.entity.Category;
import com.mszlu.service.CategoryService;
import com.mszlu.vo.CategoryVo;
import com.mszlu.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public CategoryVo findCategoryById(Long id){
        Category category = categoryMapper.selectById(id);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        categoryVo.setId(String.valueOf(category.getId()));
        return categoryVo;
    }

    @Override
    public Result findAll() {
        //没有任何参数，直接new一个空的LambdaQueryWrapper即可
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.select(Category::getId,Category::getCategoryName);
        List<Category> categoryList=categoryMapper.selectList(queryWrapper);
        return Result.success(copyList(categoryList));
    }

    @Override
    public Result findAllDetail() {
        //没有任何参数，直接new一个空的LambdaQueryWrapper即可
        List<Category> categoryList=categoryMapper.selectList(new LambdaQueryWrapper<>());
        return Result.success(copyList(categoryList));
    }

    @Override
    public Result categoryDetailById(Long id) {
        Category category=categoryMapper.selectById(id);
        return Result.success(copy(category));
    }

    public CategoryVo copy(Category category){
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        categoryVo.setId(String.valueOf(category.getId()));
        return categoryVo;
    }
    public List<CategoryVo> copyList(List<Category> categoryList){
        List<CategoryVo> categoryVoList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryVoList.add(copy(category));
        }
        return categoryVoList;
    }
}
