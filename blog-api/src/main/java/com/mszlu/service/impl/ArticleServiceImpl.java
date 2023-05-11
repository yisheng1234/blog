package com.mszlu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mszlu.dao.dos.Archives;
import com.mszlu.dao.mapper.ArticleBodyMapper;
import com.mszlu.dao.mapper.ArticleMapper;
import com.mszlu.dao.mapper.ArticleTagMapper;
import com.mszlu.entity.Article;
import com.mszlu.entity.ArticleBody;
import com.mszlu.entity.ArticleTag;
import com.mszlu.entity.SysUser;
import com.mszlu.service.*;
import com.mszlu.utils.UserThreadLocal;
import com.mszlu.vo.ArticleBodyVo;
import com.mszlu.vo.ArticleVo;
import com.mszlu.vo.Result;
import com.mszlu.vo.TagVo;
import com.mszlu.vo.params.ArticleParam;
import com.mszlu.vo.params.PageParam;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    @Autowired
    private TagService  tagService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    CategoryService categoryService;
    @Override
    public Result listArticle(PageParam pageParams){
        Page<Article> page=new Page<>(pageParams.getPage(),pageParams.getPageSize());
        IPage<Article> articleIPage=articleMapper.listArticle(
                page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());
        List<Article> articleList=articleIPage.getRecords();

        return Result.success(copyList(articleList,true,true));
    }
    //因为 查询 年，月，mybatis-plus无法支持，所以将下面所有的逻辑写到 xml中用sql语句完成（上面的方法）

//    public Result listArticle(PageParam pageParams) {
//        //分页查询article数据库表          //当前是第几页，每页的文章数量
//        Page page=new Page<>(pageParams.getPage(),pageParams.getPageSize());
//        //查询条件
//                LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
//
//        //查看分类下的文章时，回传过来CategoryId，这时需要加一个判断条件，根据分类查询文章
//        if(pageParams.getCategoryId()!=null){
//            queryWrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
//        }
//        //按tag查找文章。
//        List<Long> articleIdList=new ArrayList<>();
//        if(pageParams.getTagId()!=null){
//
//          LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper=new LambdaQueryWrapper<>();
//          articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId,pageParams.getTagId());
//          List<ArticleTag> articleTags=articleTagMapper.selectList(articleTagLambdaQueryWrapper);
//
//          for(ArticleTag articleTag:articleTags){
//              articleIdList.add(articleTag.getArticleId());
//          }
//
//          if(articleIdList.size()>0){
//              queryWrapper.in(Article::getId,articleIdList);
//          }
//
//        }
//        //order by weight,create_date desc 按照是否置顶和发布时间进行排序
//        queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
//                                //第一个参数Page,第二个参数查询条件
//        Page<Article> articlePage=articleMapper.selectPage(page,queryWrapper);
//        List<Article> records=articlePage.getRecords();
//        List<ArticleVo> list=copyList(records,true,true);
//        return Result.success(list);
//    }

  //下面两个是相同方法名，不同参数数量。 因为第一个是之前写的，要改参数会该很多之前用到的地方
    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor) {
        List<ArticleVo> articleVoList=new ArrayList<>();
        for(Article record:records){
            articleVoList.add(copy(record,isTag,isAuthor,false,false));
        }
        return articleVoList;
    }
    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory) {
        List<ArticleVo> articleVoList=new ArrayList<>();
        for(Article record:records){
            articleVoList.add(copy(record,isTag,isAuthor,isBody,isAuthor));
        }
        return articleVoList;
    }


    //将article转为ArticleVo类
    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory){
        ArticleVo articleVo=new ArticleVo();
        //把第二个参数中和第一个参数中属性名相同的复制过去
        BeanUtils.copyProperties(article,articleVo);
        articleVo.setId(String.valueOf(article.getId()));
        //两个类中的 创建时间属性  一个是long型，一个是string，所以自动转不过来，所以手动转
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd hh:MM:ss"));
//        System.out.println(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd hh:MM:ss"));
        //并不是所有接口 都需要 标签和作者这两个信息
        if(isTag){
            Long articleId = article.getId();

            articleVo.setTags(tagService.findsTagsByArticleId(articleId));
        }
        if(isAuthor){
            Long authorId= article.getAuthorId();
           articleVo.setAuthor(sysUserService.findSysUserById(authorId).getNickname());
        }
        if(isBody){
            Long bodyId=article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if(isCategory){
            Long categoryId=article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
            log.info("{}",articleVo.getCategory());
        }

        return articleVo;
    }

    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }


    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
         queryWrapper.select(Article::getId,Article::getTitle);
         //limit 后面必须留下空格
        queryWrapper.last("limit "+limit);
        //select id,title from article order by view_count desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result newArticle(int limit) {

        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        //limit 后面必须留下空格
        queryWrapper.last("limit "+limit);
        //select id,title from article order by view_count desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result listArchives() {
        List<Archives> articleList=articleMapper.listArchives();
        return Result.success(articleList);
    }

    @Autowired
    ThreadService threadServicel;
    @Override
    public Result findArticleById(Long articleId) {
        /*
        * 1.根据id查询文章信息
        * 2.根据查到的bodyid和categroyid 去做关联查询
        * */

        Article article=articleMapper.selectById(articleId);
        ArticleVo articleVo=copy(article,true,true,true,true);
        //查看完文章之后，增加阅读数，有没有问题呢？
        //查看玩文章之后，本应该直接返回数据了，这时候加了一个更新操作，更新时加写锁，阻塞其他的读操作，性能会比较低
        //更新增加了此次接口的耗时。而且一旦此更新操作出了问题，会影响到文章阅读
        //使用线程池  可以把更新操作放到线程池中去执行，和主线程就不相关了

        //期望此操作在线程池执行，不会影响原有的主线程。
        //完成之后， 不会影响查询文章的操作。将更新阅读数放到了另一个线程

        threadServicel.updateArticleViewCount(articleMapper,article);
        return Result.success(articleVo);
    }
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Override
    public Result publish(ArticleParam articleParam) {

        /*
        * 1.发布文章 目的是 构建Article对象
        * 2.作者id，当前的登录用户，用UserThreadLocal拿用户信息，前提是此请求经过了拦截器
        * 3.添加文章，标签 关联关系到关联表中
        * */
        Article article=new Article();


        SysUser sysUser= UserThreadLocal.get();
        article.setAuthorId(sysUser.getId());
        article.setWeight(Article.Article_Common);
        article.setViewCounts(0);
        article.setTitle(articleParam.getTitle());
        article.setSummary(articleParam.getSummary());
        article.setCommentCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        System.out.println("+++++++++++++++++"+System.currentTimeMillis());
        article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
        //插入之后会生成一个文章id。 然后就可以拿到id  article.getId();
        articleMapper.insert(article);
        Long articleId = article.getId();
        //tag
        List<TagVo> tags=articleParam.getTags();
        if(tags!=null){
            for(TagVo tag:tags){
                ArticleTag articleTag=new ArticleTag();
                articleTag.setArticleId(articleId);
                articleTag.setTagId(Long.parseLong(tag.getId()));
                articleTagMapper.insert(articleTag);
            }
        }
        //body
        ArticleBody articleBody=new ArticleBody();
        articleBody.setArticleId(articleId);
        //使用的是markdown编辑器，会根据左边内容生成htlm格式的代码
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);
        article.setBodyId(articleBody.getId());



        //最后要更新
        articleMapper.updateById(article);

        Map<String,String> map=new HashMap<>();
        //放string还是因前端的精度损失问题
        map.put("id",article.getId().toString());
        return Result.success(map);
    }
}
