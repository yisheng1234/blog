package com.mszlu.service;

import com.mszlu.entity.Admin;
import com.mszlu.entity.Permission;
import com.mszlu.vo.Result;
import com.mszlu.vo.params.PageParam;

import java.util.List;

public interface AdminService {
    Admin findUserByName(String name);

    List<Permission> findPermissionByAdminId(Long id);

    Result listAdmin(PageParam pageParam);

    Result addAdmin(Admin admin);
}
