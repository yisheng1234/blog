package com.mszlu.service;

import com.mszlu.vo.Result;
import com.mszlu.vo.params.ArticleParam;
import com.mszlu.vo.params.PageParam;

public interface ArticleService {
    /*
    * 分页查询文章列表
    * */
    Result listArticle(PageParam pageParams);
    /*
    * 最热文章
    * */
    Result hotArticle(int limit);
/*
* 最新文章
* */
    Result newArticle(int limit);
/*
* 首页文章归档
* */
    Result listArchives();
    /*
     * 查看文章信息
     * */
    Result findArticleById(Long articleId);
/*
* 发布文章
* */
    Result publish(ArticleParam articleParam);
}
