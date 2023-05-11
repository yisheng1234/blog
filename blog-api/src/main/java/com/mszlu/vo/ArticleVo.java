package com.mszlu.vo;

import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {
    //解决前端精度损失问题  object类型不能使用这个注解。
    //用第二种方法，将 vo类中的id属性都改为String类型
//    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;
    /**
     * 创建时间
     */
    private String createDate;

    private String author;

    private ArticleBodyVo body;

    private List<TagVo> tags;

    private CategoryVo category;

}