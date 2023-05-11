package com.mszlu.controller;

import com.mszlu.service.CommentsService;
import com.mszlu.vo.Result;
import com.mszlu.vo.params.CommentParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags="评论")
@RestController
@RequestMapping("comments")
public class CommentsController {
    @Autowired
    private CommentsService commentsService;
    @ApiOperation(value = "根据文章id查找评论")
    @GetMapping ("article/{id}")
    public Result comments(@PathVariable("id") Long id){
        return commentsService.commentsByArticleId(id);
    }
    @PostMapping("create/change")
    @ApiOperation(value = "写评论")
    public Result comment(@RequestBody CommentParam commentParam){
        /*评论功能*/
        return commentsService.comment(commentParam);
    }
}
