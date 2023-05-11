package com.mszlu.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mszlu.entity.Tag;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {
    List<Tag> findTagsByArticleId(Long id);
    List<Long> findHotsTagIds(int limit);
    List<Tag> findTagsByTagIds(List<Long> tagIds);
}
