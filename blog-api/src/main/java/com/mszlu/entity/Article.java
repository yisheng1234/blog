package com.mszlu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//指定关联表
@TableName("ms_article")
//不指定也可以，我们已经在yml文件中指定可表名的前缀，再通过类名与表名后缀的对应关系就能自动找到
public class Article {

    public static final int Article_TOP = 1;

    public static final int Article_Common = 0;
    private Long id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    /**
     * 作者id
     */
    private Long authorId;
    /**
     * 内容id
     */
    private Long bodyId;
    /**
     *类别id
     */
    private Long categoryId;

    /**
     * 置顶
     */
    private Integer weight ;


    /**
     * 创建时间
     */
//    @TableField("CREATE_DATE")
    private Long createDate;
}