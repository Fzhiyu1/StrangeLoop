package com.xiaofeng.strangeloop.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaofeng.strangeloop.system.domain.ModelMessage;
import com.xiaofeng.strangeloop.system.mapper.ModelMessageMapper;
import com.xiaofeng.strangeloop.system.service.ModelMessageService;
import org.springframework.stereotype.Service;

//使用mybatis-plus示例serviceImpl
@Service
public class ModelMessageServiceImpl extends ServiceImpl<ModelMessageMapper, ModelMessage> implements ModelMessageService {
}
