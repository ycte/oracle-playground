package com.op.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.op.common.lang.Result;
import com.op.entity.Blog;
import com.op.entity.Commit;
import com.op.mapper.CommitMapper2;
import com.op.model.CommitVO;
import com.op.service.BlogService;
import com.op.service.CommitService;
import com.op.service.CommitService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
public class CommitController2 {
    @Autowired
    CommitService2 commitService;

    //    @Test
//    void testSelectOrders() { List<CommitVO> orderVOS = commitMapper.selectCommits(); }
    @GetMapping("/commits/{id}")
    public Result commit(@PathVariable(name = "id") Long id) {
//        if(currentPage == null || currentPage < 1) currentPage = 1;
        System.out.println("commit"+id);
        Page page = new Page(1, 1000);
        IPage pageData = commitService.findPage(page, new QueryWrapper<CommitVO>().ne("status",-1).eq("blog_id", id).orderByDesc("created"));
//        List<CommitVO> commitVOS = commitMapper.selectCommits();
        //        System.out.println(pageData);
        return Result.succ(pageData);
    }
}
