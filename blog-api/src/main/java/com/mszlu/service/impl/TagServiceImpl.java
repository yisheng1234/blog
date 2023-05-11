package com.mszlu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.entity.Tag;
import com.mszlu.dao.mapper.TagMapper;
import com.mszlu.service.TagService;
import com.mszlu.vo.Result;
import com.mszlu.vo.TagVo;
import io.micronaut.core.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;
    @Override
    public List<TagVo> findsTagsByArticleId(Long id) {
        //mybatis-plugs无法用于多表查询
        List<Tag> tags=tagMapper.findTagsByArticleId(id);
        return copyList(tags);
    }

    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        tagVo.setId(String.valueOf(tag.getId()));
        return tagVo;
    }

    @Override
    public Result hots(int limit) {
        //标签所拥有的文章最多就是最热标签
        // 根据tag_id分组，计数，从大到小排列

        //得到最热的tag的id
        List<Long> tagIds=tagMapper.findHotsTagIds(limit);
        if(CollectionUtils.isEmpty(tagIds)){
            //如果为空，就返回空，因为之后的sql语句不能用空的id
            return Result.success(Collections.emptyList());

        }
        //根据tag_id得到tag名字，
        List<Tag> tags=tagMapper.findTagsByTagIds(tagIds);
        return Result.success(tags);
    }

    @Override
    public Result findAll() {
        LambdaQueryWrapper<Tag> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.select(Tag ::getId,Tag::getTagName);
        List<Tag> tagList=tagMapper.selectList(queryWrapper);

        return Result.success(copyList(tagList));
    }

    @Override
    public Result findAllDetail() {
        List<Tag> tagList=tagMapper.selectList(new LambdaQueryWrapper<>());


        return Result.success(copyList(tagList));
    }

    @Override
    public Result findDetailById(Long id) {
        Tag tag=tagMapper.selectById(id);
        return Result.success(tag);
    }
}
