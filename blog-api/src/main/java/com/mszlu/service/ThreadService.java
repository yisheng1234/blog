package com.mszlu.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.mszlu.dao.mapper.ArticleMapper;
import com.mszlu.entity.Article;
import io.micronaut.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component

public class ThreadService {
    @Async("taskExecutor")   //拿到在ThreadPoolConfig中创建的多线程对象
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article)  {

        int viewCounts=article.getViewCounts();

        //下面的update操作需要传两个数据，这是第一个
        //一个实体类，这个是实体类设置了哪些属性，就修改成这些属性的值,注意如果实体类中属性类型为int，会自动设置为0，所以要使用Interger
        Article articleUpdate=new Article();
        articleUpdate.setViewCounts(viewCounts+1);

        //设置跟新条件
        LambdaUpdateWrapper<Article> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId,article.getId());
        //设置一个 为了在多线程环境下的线程安全，防止读脏数据
        updateWrapper.eq(Article::getViewCounts,viewCounts);

        articleMapper.update(articleUpdate,updateWrapper);



    }
}
