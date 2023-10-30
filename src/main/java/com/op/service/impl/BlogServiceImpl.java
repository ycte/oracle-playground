package com.op.service.impl;

import com.op.entity.Blog;
import com.op.mapper.BlogMapper;
import com.op.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Author: @Xiaoxi
 * @since 2023-06-17
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
