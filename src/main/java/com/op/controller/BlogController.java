package com.op.controller;


import com.op.common.lang.Result;
import com.op.entity.Blog;
import com.op.service.BlogService;
import com.op.util.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.api.R;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Date;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Author: @Xiaoxi
 * @since 2023-06-17
 */
@RestController
public class BlogController {
    @Autowired
    BlogService blogService;
    @GetMapping("/blogs")
    public Result blogs(Integer currentPage) {
        if(currentPage == null || currentPage < 1) currentPage = 1;
        Page page = new Page(currentPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().ne("status",-1).orderByDesc("created"));
//        System.out.println(pageData);
        return Result.succ(pageData);
    }
    @GetMapping("/blogs/search")
    public Result searchBlogs(String keyword, Integer currentPage) {
        if(keyword == null) {
            keyword = "";
        }
        if(currentPage == null || currentPage < 1) currentPage = 1;
        Page page = new Page(currentPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>()
                .ne("status",-1)
                .like("title",keyword)
                .orderByDesc("created"));
        return Result.succ(pageData);
    }
    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客不存在！");
        Assert.isTrue(blog.getStatus() != -1, "该博客已删除！");
        return Result.succ(blog);
    }
    @RequiresAuthentication
    @GetMapping("/blogdel/{id}")
    public Result delete(@Validated @PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
//        System.out.println(blog.toString());
        Assert.notNull(blog, "该博客不存在！");
        Assert.isTrue(blog.getStatus() != -1, "该博客已删除！");
//        System.out.println(blog.getUserId());
//        System.out.println(ShiroUtil.getProfile());
        Assert.isTrue(blog.getUserId() == ShiroUtil.getProfile().getId(), "没有权限删除");
        blog.setStatus(-1);
        blogService.saveOrUpdate(blog);
        return Result.succ(blog);
    }
    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog) {
        System.out.println(blog.toString());
        Blog temp = null;
        if(blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            Assert.isTrue(temp.getUserId() == ShiroUtil.getProfile().getId(), "没有权限编辑");
        } else {
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);
        return Result.succ("操作成功", null);
    }
}

