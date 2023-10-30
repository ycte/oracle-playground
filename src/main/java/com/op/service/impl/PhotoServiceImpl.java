package com.op.service.impl;

import com.op.entity.Photo;
import com.op.mapper.PhotoMapper;
import com.op.service.PhotoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Author: @Xiaoxi
 * @since 2023-06-28
 */
@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo> implements PhotoService {

}
