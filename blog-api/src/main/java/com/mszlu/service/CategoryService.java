package com.mszlu.service;

import com.mszlu.vo.CategoryVo;
import com.mszlu.vo.Result;

public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);

    Result findAll();

    Result findAllDetail();

    Result categoryDetailById(Long id);
}
