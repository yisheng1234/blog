package com.mszlu.controller;


import com.mszlu.service.TagService;
import com.mszlu.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Api(tags="标签")
@RestController
@RequestMapping("/tags")
public class TagsController {
    @Autowired
    TagService tagService;
    @GetMapping
    @ApiOperation(value = "获取所有标签")
    public Result findAll(){
        return tagService.findAll();
    }
    @GetMapping("detail")
    @ApiOperation(value = "所有标签信息")
    public Result findAllDetail(){
        return tagService.findAllDetail();
    }
    @GetMapping("detail/{id}")
    @ApiOperation(value = "根据id查看标签信息")
    public Result findDetailById(@PathVariable("id") Long id){
        return tagService.findDetailById(id);
    }
    @GetMapping("/hot")
    @ApiOperation(value = "最热标签（2个）")
    public Result hot(){
        /*
        * 最热标签
        * */
        //查询最热的2个标签
        int limit=2;
        return tagService.hots(limit);
    }

}
