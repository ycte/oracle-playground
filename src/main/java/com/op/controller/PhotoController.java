package com.op.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.op.common.lang.Result;
import com.op.entity.Notify;
import com.op.entity.Photo;
import com.op.service.NotifyService;
import com.op.service.PhotoService;
import com.op.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Author: @Xiaoxi
 * @since 2023-06-28
 */
@RestController
//@RequestMapping("/photo")
public class PhotoController {
    @Autowired
    PhotoService notifyService;
    @GetMapping("/photo")
    public Result notify(Integer currentPage) {
        if(currentPage == null || currentPage < 1) currentPage = 1;
        Page page = new Page(currentPage, 5);
        IPage pageData = notifyService.page(page, new QueryWrapper<Photo>().ne("status",-1).orderByDesc("created"));
//        System.out.println(pageData);
        return Result.succ(pageData);
    }
    @GetMapping("/photo/search")
    public Result searchBlogs(String keyword, Integer currentPage) {
        if(keyword == null) {
            keyword = "";
        }
        if(currentPage == null || currentPage < 1) currentPage = 1;
        Page page = new Page(currentPage, 5);
        IPage pageData = notifyService.page(page, new QueryWrapper<Photo>()
                .ne("status",-1)
                .like("title",keyword)
                .orderByDesc("created"));
        return Result.succ(pageData);
    }
    @GetMapping("/photo/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Photo notify = notifyService.getById(id);
        Assert.notNull(notify, "该照片不存在！");
        Assert.isTrue(notify.getStatus() != -1, "该照片已删除！");
        return Result.succ(notify);
    }
    @RequiresAuthentication
    @GetMapping("/photodel/{id}")
    public Result delete(@Validated @PathVariable(name = "id") Long id) {
        Photo blog = notifyService.getById(id);
//        System.out.println(blog.toString());
        Assert.notNull(blog, "该照片不存在！");
        Assert.isTrue(blog.getStatus() != -1, "该照片已删除！");
//        System.out.println(blog.getUserId());
//        System.out.println(ShiroUtil.getProfile());
        Assert.isTrue(blog.getUserId() == ShiroUtil.getProfile().getId(), "没有权限删除");
        blog.setStatus(-1);
        notifyService.saveOrUpdate(blog);
        return Result.succ(blog);
    }
    @RequiresAuthentication
    @PostMapping("/photo/edit")
    public Result edit(@Validated @RequestBody Photo blog) {
        System.out.println(blog.toString());
        Photo temp = null;
        if(blog.getId() != null) {
            temp = notifyService.getById(blog.getId());
            Assert.isTrue(temp.getUserId() == ShiroUtil.getProfile().getId(), "没有权限编辑");
        } else {
            temp = new Photo();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        notifyService.saveOrUpdate(temp);
        return Result.succ("操作成功", null);
    }
}
