package com.op.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.op.model.CommitVO;

public interface CommitService2 {
    IPage<CommitVO> findPage(Page<CommitVO> page, QueryWrapper<CommitVO> queryWrapper);
}
