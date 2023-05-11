package com.mszlu.controller;


import com.mszlu.common.cache.Cache;
import com.mszlu.common.log.LogAnnotation;
import com.mszlu.service.ArticleService;
import com.mszlu.vo.Result;
import com.mszlu.vo.params.ArticleParam;
import com.mszlu.vo.params.PageParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//如果这个控制器中没有页面跳转之类的，都是返回json数据，就用RestController
@Api(tags="文章")
@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    /*
    * 首页 文章列表
    * */
    @PostMapping
    //打印日志注解，自己开发的，在aop包下。  创建这个类时 选注解类
    @LogAnnotation(module="文章",operator="获取文章列表")
    @Cache(expire = 5*5*1000,name = "hot_article")
//    @ApiImplicitParam(name = "name",value = "姓名",required = true)
    @ApiOperation(value = "获取首页文章列表")
    public Result listArticle(@RequestBody PageParam pageParams){
        return articleService.listArticle(pageParams);
    }
    /*
     * 首页最热文章
     * */
    @PostMapping("hot")
    @ApiOperation(value = "获取首页最热文章")
    @Cache(expire = 5*5*1000,name = "hot_article")
    public Result hotArticle(){
        int limit=5;
        return articleService.hotArticle(limit);
    }
    /*
     * 首页最新文章
     * */
    @PostMapping("new")
    @ApiOperation(value = "获取首页最新文章")
    public Result newArticle(){
        int limit=5;
        return articleService.newArticle(limit);
    }
    /*
     * 首页文章归档,统计某年某月发布了多少条文章
     * */
    @PostMapping("listArchives")
    @ApiOperation(value = "获取首页归档")
    public Result listArchives(){
        return articleService.listArchives();
    }

    @PostMapping("view/{id}")
    /*
    * 查看文章信息
    * */
    @ApiOperation(value = "查看文章信息")
    public Result findArticleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }

    @ApiOperation(value = "发布文章")
    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        /*发布文章*/
        return articleService.publish(articleParam);
    }
}
