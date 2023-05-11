package com.mszlu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.dao.mapper.CommentMapper;
import com.mszlu.entity.Comment;
import com.mszlu.entity.SysUser;
import com.mszlu.service.CommentsService;
import com.mszlu.service.SysUserService;
import com.mszlu.utils.UserThreadLocal;
import com.mszlu.vo.CommentVo;
import com.mszlu.vo.Result;
import com.mszlu.vo.UserVo;
import com.mszlu.vo.params.CommentParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private SysUserService sysUserService;
    @Override
    public Result commentsByArticleId(Long id) {
        /*
        * 1.根据文章id从comment表查询评论
        * 2.根据评论者的id查询作者信息
        * 3.如果level=1 要去查询他有没有子评论，
        * 4.如果有查询
        * */
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,id);
        //一级评论
        queryWrapper.eq(Comment::getLevel,1);
        List<Comment> comments=commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVoList=copyList(comments);
        return Result.success(commentVoList);
    }



    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList=new ArrayList<>();
        for(Comment comment:comments){
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo=new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        commentVo.setId(String.valueOf(comment.getId()));
        //作者信息
        UserVo userVo=sysUserService.findUserVoById(comment.getAuthorId());
        commentVo.setAuthor(userVo);
        //子评论
        if(1==comment.getLevel()) {
            List<CommentVo> commentVoList = findCommentsByParentId(comment.getId());
            commentVo.setChildrens(commentVoList);
        }
        //to user 给谁评论的  1级是最高的，子评论是二级
        if(comment.getLevel()>1){
            Long toUid = comment.getToUid();
            UserVo toUserVo= sysUserService.findUserVoById(toUid);
            commentVo.setToUser(toUserVo);

        }
        return commentVo;
    }

    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,id);
        queryWrapper.eq(Comment::getLevel,2);
        //copy方法中调用这个，这个再去调用copy方法。类似于递归
        return copyList(commentMapper.selectList(queryWrapper));
    }
    @Override
    public Result comment(CommentParam commentParam) {
        //再拦截器中   往UserThreadlocal中添加了用户信息
        SysUser sysUser= UserThreadLocal.get();
        Comment comment=new Comment();

        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        System.out.println("-----------------------"+comment+"-------------------");
        commentMapper.insert(comment);
        return Result.success(null);
    }
}
