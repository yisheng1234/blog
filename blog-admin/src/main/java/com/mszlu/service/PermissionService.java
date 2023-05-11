package com.mszlu.service;

import com.mszlu.entity.Permission;
import com.mszlu.vo.Result;
import com.mszlu.vo.params.PageParam;

public interface PermissionService {

    Result listPermission(PageParam pageParam);

    Result add(Permission permission);

    Result update(Permission permission);

    Result delete(Long id);
}
