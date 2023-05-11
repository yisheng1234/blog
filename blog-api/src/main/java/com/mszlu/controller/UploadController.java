package com.mszlu.controller;


import com.mszlu.utils.QiniuUtils;
import com.mszlu.utils.TencentCOSUtils;
import com.mszlu.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@Api(tags="文件上传腾讯云")
@RestController
@RequestMapping("upload")
public class UploadController {
    @Autowired
    private QiniuUtils qiniuUtils;
    @Autowired
    private TencentCOSUtils tencentCOSUtils;
    @PostMapping
    //MultipartFile专门用于接收文件
    @ApiOperation(value = "文件上传腾讯云")
    public Result upload(@RequestParam("image") MultipartFile file){
//        //获取原始文件名称 比如 aa.png
//        String originalFilename = file.getOriginalFilename();
//        //保证图片名不会重复，UUID在笔记                                         //拿到 originalFilename . 后面的数据 也就是后缀
//        String filename= UUID.randomUUID().toString()+"." + StringUtils.substringAfterLast(originalFilename,".");
        //上传文件，上传到哪里？ 上传到七牛云，云服务器，按量付费，速度快，把图片发到离用户最近的服务器上
        //降低我们自身应用服务器的带宽消耗
        String fileName = tencentCOSUtils.uploadfile(file);
        System.out.println(TencentCOSUtils.url+fileName);
        return Result.success(TencentCOSUtils.url+fileName);
    }
}
