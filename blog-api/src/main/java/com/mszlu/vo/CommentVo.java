package com.mszlu.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

@Data
public class CommentVo  {
    /*
    * 用户添加二级评论后，往数据库添加评论时，由于mybatis-plus分布式生成id，可能生成的id位数太多，前端
    * 接收不了，导致一级评论的id时错误的。造成parent_id 出现错误
    *
    * 解决方法，序列化，把id转为string。可以防止前端精度损失
    * */
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private UserVo author;

    private String content;

    private List<CommentVo> childrens;

    private String createDate;

    private Integer level;

    private UserVo toUser;
}
