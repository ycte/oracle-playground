package com.op.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.op.entity.Commit;
import com.op.mapper.CommitMapper2;
import com.op.model.CommitVO;
import com.op.service.CommitService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommitServiceImpl2 extends ServiceImpl<CommitMapper2, Commit> implements CommitService2 {
    @Autowired
    private CommitMapper2 commitMapper;


    @Override
    public IPage<CommitVO> findPage(Page<CommitVO> page, QueryWrapper<CommitVO> queryWrapper) {
        return baseMapper.findPage(page, queryWrapper);
    }
}