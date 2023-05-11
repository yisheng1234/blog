package com.mszlu.controller;

import com.mszlu.service.CategoryService;
import com.mszlu.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Api(tags="文章分类")
@RestController
@RequestMapping("categorys")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    @ApiOperation(value = "获取所有文章分类")
    public Result categories(){
        return categoryService.findAll();
    }
    @GetMapping("detail")
    @ApiOperation(value = "分类详细信息")
    public Result categoriesDetail(){
        return categoryService.findAllDetail();
    }
    @GetMapping("detail/{id}")
    @ApiOperation(value = "根据id查询分类详细信息")
    public  Result categoryDetailById(@PathVariable("id") Long id){
        return  categoryService.categoryDetailById(id);
    }
}
