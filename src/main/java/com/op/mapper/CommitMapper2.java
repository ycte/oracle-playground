package com.op.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.op.entity.Commit;
import com.op.model.CommitVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//public interface CommitMapper2 extends BaseMapper<Commit> {
//    // 查询订单列表 List<OrderVO> selectOrders();
//    List<CommitVO> selectCommits();
@Mapper
@Repository
public interface CommitMapper2 extends BaseMapper<Commit> {
    //与数据库交互
    IPage<CommitVO> findPage(IPage<CommitVO> page, @Param(Constants.WRAPPER) QueryWrapper<CommitVO> wrapper);
}
//}
