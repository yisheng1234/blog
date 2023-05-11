package com.mszlu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mszlu.entity.Admin;
import com.mszlu.entity.Permission;

import java.util.List;

public interface AdminMapper extends BaseMapper<Admin> {
    List<Permission> findPermissionByAdminId(Long id);
}
