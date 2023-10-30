package com.op.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.op.common.lang.Result;
import com.op.entity.Blog;
import com.op.entity.Commit;
import com.op.entity.User;
import com.op.mapper.CommitMapper2;
import com.op.model.CommitVO;
import com.op.service.BlogService;
import com.op.service.CommitService;
import com.op.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Author: @Xiaoxi
 * @since 2023-06-29
 */
@RestController
//@RequestMapping("/commit")
public class CommitController {
    @Autowired
    CommitService commitService;
    @Autowired
    private CommitMapper2 commitMapper;

    //    @Test
//    void testSelectOrders() { List<CommitVO> orderVOS = commitMapper.selectCommits(); }
    @GetMapping("/commit/{id}")
    public Result commit(@PathVariable(name = "id") Long id) {
//        if(currentPage == null || currentPage < 1) currentPage = 1;
        System.out.println("commit"+id);
        Page page = new Page(1, 1000);
        IPage pageData = commitService.page(page, new QueryWrapper<Commit>().ne("status",-1).eq("blog_id", id).orderByDesc("created"));
//        List<CommitVO> commitVOS = commitMapper.selectCommits();
        //        System.out.println(pageData);
        return Result.succ(pageData);
    }
//    @RequiresAuthentication
    @GetMapping("/commit/edit")
    public Result edit(Long userid, Long blogid, String text) {
        System.out.println(text.toString());
        System.out.println(blogid);
        System.out.println(userid);
        Commit temp = new Commit();
        if(1==1) {
            Commit maxCommit = commitService.getOne(new QueryWrapper<Commit>().orderByDesc("id").last("LIMIT 1"));
            temp.setId(maxCommit.getId()+1);
            temp.setBlogId(blogid);
            temp.setUserId(userid);
            temp.setStatus(0);
            temp.setCreated(LocalDateTime.now());
            temp.setText(text);
        }
        commitService.saveOrUpdate(temp);
        return Result.succ("操作成功", null);
    }
}
