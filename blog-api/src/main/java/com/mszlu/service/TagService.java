package com.mszlu.service;

import com.mszlu.vo.Result;
import com.mszlu.vo.TagVo;

import java.util.List;

public interface TagService {
    /*
    *
    * 根据文章id查询标签列表
    * */
    List<TagVo> findsTagsByArticleId(Long id);
    /*
    * z最热标签
    * */
    Result hots(int limit);

    Result findAll();

    Result findAllDetail();

    Result findDetailById(Long id);
}
