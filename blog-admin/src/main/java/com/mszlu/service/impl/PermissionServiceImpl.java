package com.mszlu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mszlu.entity.Permission;
import com.mszlu.mapper.PermissionMapper;
import com.mszlu.service.PermissionService;
import com.mszlu.vo.PageResult;
import com.mszlu.vo.Result;
import com.mszlu.vo.params.PageParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public Result listPermission(PageParam pageParam) {
        Page<Permission>  page=new Page<>(pageParam.getCurrentPage(),pageParam.getPageSize());
        LambdaQueryWrapper<Permission> queryWrapper=new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(pageParam.getQueryString())){
            queryWrapper.eq(Permission::getName,pageParam.getQueryString());
        }
        Page<Permission> permissionPage=permissionMapper.selectPage(page,queryWrapper);
        PageResult<Permission> permissionPageResult=new PageResult<>();
        permissionPageResult.setList(permissionPage.getRecords());
        permissionPageResult.setTotal(permissionPage.getTotal());

        return Result.success(permissionPageResult);
    }
    public Result add(Permission permission) {
        this.permissionMapper.insert(permission);
        return Result.success(null);
    }

    public Result update(Permission permission) {
        this.permissionMapper.updateById(permission);
        return Result.success(null);
    }


    public Result delete(Long id) {
        this.permissionMapper.deleteById(id);
        return Result.success(null);
    }
}
