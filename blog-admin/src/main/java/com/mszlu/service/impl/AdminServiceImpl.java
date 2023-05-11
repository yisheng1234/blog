package com.mszlu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mszlu.entity.Admin;
import com.mszlu.entity.Permission;
import com.mszlu.mapper.AdminMapper;
import com.mszlu.service.AdminService;
import com.mszlu.vo.PageResult;
import com.mszlu.vo.Result;
import com.mszlu.vo.params.PageParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;


    @Override
    public Admin findUserByName(String name) {
        LambdaQueryWrapper<Admin> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername,name);
        queryWrapper.last("limit 1");
        Admin admin=adminMapper.selectOne(queryWrapper);
        return admin;
    }

    @Override
    public List<Permission> findPermissionByAdminId(Long id) {

        return adminMapper.findPermissionByAdminId(id);
    }

    @Override
    public Result listAdmin(PageParam pageParam) {
        Page<Admin> page=new Page<Admin>(pageParam.getCurrentPage(),pageParam.getPageSize());
        LambdaQueryWrapper<Admin> lambdaQueryWrapper=new LambdaQueryWrapper<Admin>();
        if(StringUtils.isNotBlank(pageParam.getQueryString())){
            lambdaQueryWrapper.eq(Admin::getUsername,pageParam.getQueryString());
        }
        Page<Admin> adminPage=adminMapper.selectPage(page,lambdaQueryWrapper);
        PageResult<Admin> pageResult=new PageResult<Admin>();
        pageResult.setList(adminPage.getRecords());
        pageResult.setTotal(adminPage.getTotal());
        return Result.success(pageResult);
    }

    @Override
    public Result addAdmin(Admin admin) {
        System.out.println(admin);
        adminMapper.insert(admin);

        return Result.success(null);
    }
}
