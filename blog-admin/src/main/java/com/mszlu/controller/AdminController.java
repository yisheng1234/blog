package com.mszlu.controller;

import com.mszlu.entity.Admin;
import com.mszlu.entity.Permission;
import com.mszlu.service.AdminService;
import com.mszlu.service.PermissionService;
import com.mszlu.vo.Result;
import com.mszlu.vo.params.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private AdminService adminService;
    @PostMapping("permission/permissionList")
    public Result listPermission(@RequestBody PageParam pageParam){

        return permissionService.listPermission(pageParam);
    }
    @PostMapping("permission/add")
    public Result add(@RequestBody Permission permission){
        return permissionService.add(permission);
    }
    @PostMapping("permission/update")
    public Result update(@RequestBody Permission permission){
        return permissionService.update(permission);
    }
    @GetMapping("permission/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return permissionService.delete(id);
    }

    @PostMapping("adminList")
    public Result listAdmin(@RequestBody PageParam pageParam){
        return adminService.listAdmin(pageParam);
    }
    @PostMapping("add")
    public Result addAdmin(@RequestBody Admin admin){
        System.out.println(admin);
        return adminService.addAdmin(admin);
    }
}
