package com.op.controller;

import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.op.common.dto.LoginDto;
import com.op.common.lang.Result;
import com.op.entity.User;
import com.op.service.UserService;
import com.op.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.executable.ValidateOnExecution;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AccountController {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;
    /**
     * 默认账号密码：markerhub / 111111
     *
     */
    @CrossOrigin
    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        System.out.println(loginDto.getUsername());
        Assert.notNull(user, "用户不存在");
//        System.out.println(user.getPassword() + SecureUtil.md5(loginDto.getPassword()));
        if(!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            return Result.fail("密码错误！");
        }
        String jwt = jwtUtils.generateToken(user.getId());
        System.out.println(jwt);
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        // 用户可以另一个接口
        Map<Object, Object> optionMap1 = MapUtil.builder()
                .put("root", false)
                .map();
//        Map<Object, Object> resMap = MapUtil.builder()
//                .put("role", MapUtil.builder()
//                        .put("/blogs", MapUtil.builder()
//                                .put("value", MapUtil.builder()
//                                        .put("total", true)
//                                        .map())
//                        )
////                                .put("options", MapUtil.builder()
////                                        .put("root", false)
////                                        .map()))
//                        .put("/admin", false)
//                        .map())
//                .put("info", MapUtil.builder()
//                        .put("id", user.getId())
//                        .put("username", user.getUsername())
//                        .put("avatar", user.getAvatar())
//                        .put("email", user.getEmail())
//                        .map())
//                .put("a", "a")
//                .map();
        Map<Object, Object> resMap = new HashMap<Object, Object>();
        Map<Object, Object> infoMap = new HashMap<Object, Object>();
        Map<Object, Object> blogMap = new HashMap<Object, Object>();
        Map<Object, Object> blogOptMap = new HashMap<Object, Object>();
        Map<Object, Object> adminMap = new HashMap<Object, Object>();
        Map<Object, Object> roleMap = new HashMap<Object, Object>();
//        // user 的权限返回
        if(user.getStatus() == 1) {
            infoMap.put("id", user.getId());
            infoMap.put("username", user.getUsername());
            infoMap.put("avatar", user.getAvatar());
            infoMap.put("email", user.getEmail());
            resMap.put("info", infoMap); // userinfo.info
            blogMap.put("value", true);
            blogOptMap.put("root", false);
            blogMap.put("options", blogOptMap);
            roleMap.put("/blogs", blogMap); // userinfo.role./blogs
            adminMap.put("value", false);
            roleMap.put("/admin", adminMap); // userinfo.role./admin
            resMap.put("role", roleMap);
        }
        // admin
        if (user.getStatus() == 0) {

            infoMap.put("id", user.getId());
            infoMap.put("username", user.getUsername());
            infoMap.put("avatar", user.getAvatar());
            infoMap.put("email", user.getEmail());
            resMap.put("info", infoMap); // userinfo.info
            blogMap.put("value", true);
            blogOptMap.put("root", true);
            blogMap.put("options", blogOptMap);
            roleMap.put("/blogs", blogMap); // userinfo.role./blogs
            adminMap.put("value", true);
            roleMap.put("/admin", adminMap); // userinfo.role./admin
            resMap.put("role", roleMap);
        }
        return Result.succ(resMap
        );
    }

    // 退出
    @GetMapping("/logout")
    @RequiresAuthentication
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }
}
