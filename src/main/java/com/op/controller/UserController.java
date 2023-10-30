package com.op.controller;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.op.common.dto.chgPasswdDto;
import com.op.common.lang.Result;
import com.op.entity.Blog;
import com.op.entity.User;
import com.op.service.UserService;
import com.op.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Author: @Xiaoxi
 * @since 2023-06-17
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/{id}")
    public Object test(@PathVariable("id") Long id) {
        return userService.getById(id);
    }
    @PostMapping("/save")
    public Object testUser(@Validated @RequestBody User user) {
        return user.toString();
    }
    @PostMapping("/register")
    public Object register(@Validated @RequestBody User user) {
        User temp = new User();

        if (userService.count(new QueryWrapper<User>().eq("username", user.getUsername())) == 0) {
            User maxUser = userService.getOne(new QueryWrapper<User>().orderByDesc("id").last("LIMIT 1"));
            System.out.println(maxUser.getId());
//            assert temp != null;
            temp.setId(maxUser.getId()+1);
            System.out.println(temp.getId());
            temp.setPassword(SecureUtil.md5(user.getPassword()));
            temp.setUsername(user.getUsername());
            temp.setStatus(1);
            temp.setEmail(user.getEmail());
            temp.setAvatar(user.getAvatar());
            temp.setCreated(LocalDateTime.now());
//            userService.saveOrUpdate(temp);
            userService.saveOrUpdate(temp);
            return Result.succ("操作成功", null);
        }
        else {
            return Result.fail("用户名已存在！");
        }
    }
    @PostMapping("/edit")
    public Object editUser(@Validated @RequestBody User user) {
        System.out.println(user);
        User temp = null;
        if (user.getId() != null) {
            temp = userService.getById(user.getId());
            Assert.isTrue(temp.getId() == ShiroUtil.getProfile().getId(), "没有权限");
            temp.setAvatar(user.getAvatar());
            temp.setEmail(user.getEmail());
            temp.setUsername(user.getUsername());
        }
        userService.saveOrUpdate(temp);
        return Result.succ("操作成功", null);
    }
    @PostMapping("/chgpasswd")
    public Object chgPasswd(@Validated @RequestBody chgPasswdDto chgPasswdDto) {
//        System.out.println("hahah");
        User temp = null;
        if (chgPasswdDto.getId() != null) {
//            System.out.println(chgPasswdDto.getId());
            temp = userService.getById(chgPasswdDto.getId());
            System.out.println(temp.getPassword());
            System.out.println(SecureUtil.md5(chgPasswdDto.getOldpassword()));
            if(!temp.getPassword().equals(SecureUtil.md5(chgPasswdDto.getOldpassword()))) {
                return Result.fail("密码错误！");
            }
            if(!chgPasswdDto.getPassword().equals(chgPasswdDto.getPassword2())) {
                return  Result.fail("密码不一致");
            }
            System.out.println(SecureUtil.md5(chgPasswdDto.getPassword()));
            temp.setPassword(SecureUtil.md5(chgPasswdDto.getPassword()));
        }
        userService.saveOrUpdate(temp);
        return Result.succ("操作成功", null);
    }
    @GetMapping("/getalluser")
    @RequiresAuthentication
    public Result getAllUser(Integer currentPage) {
        System.out.println(1);
        if(currentPage == null || currentPage < 1) currentPage = 1;
        Page page = new Page(currentPage, 5);
        IPage pageData = userService.page(page, new QueryWrapper<User>().ne("status",-1).orderByDesc("created"));
        System.out.println(pageData);
        return Result.succ(pageData);
    }
    @GetMapping("/users/search")
    @RequiresAuthentication
    public Result searchBlogs(String keyword, Integer currentPage) {
        if(keyword == null) {
            keyword = "";
        }
        if(currentPage == null || currentPage < 1) currentPage = 1;
        Page page = new Page(currentPage, 5);
        IPage pageData = userService.page(page, new QueryWrapper<User>()
                .ne("status",-1)
                .like("username",keyword)
                .orderByDesc("created"));
        return Result.succ(pageData);
    }
    @GetMapping("/setadmin")
    @RequiresAuthentication
    public Result setAdmin(Long id) {
        User temp = null;
        if (id != null) {
            temp = userService.getById(id);
            temp.setStatus(0);
        }
        userService.saveOrUpdate(temp);
        return Result.succ("操作成功", null);
    }
    @GetMapping("/setasuser")
    @RequiresAuthentication
    public Result setAsUser(Long id) {
        User temp = null;
        if (id != null) {
            temp = userService.getById(id);
            temp.setStatus(1);
        }
        userService.saveOrUpdate(temp);
        return Result.succ("操作成功", null);
    }
}
