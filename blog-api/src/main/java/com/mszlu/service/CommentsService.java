package com.mszlu.service;

import com.mszlu.vo.Result;
import com.mszlu.vo.params.CommentParam;

public interface CommentsService {
    /*
    * 根据文章id，查询所有评论列表
    * */
     Result commentsByArticleId(Long id);
    /*
    * 评论文章功能
    * */
    Result comment(CommentParam commentParam);
}
