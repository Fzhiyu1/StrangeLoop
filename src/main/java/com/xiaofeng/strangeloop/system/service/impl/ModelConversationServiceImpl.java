package com.xiaofeng.strangeloop.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaofeng.strangeloop.system.domain.ModelConversation;
import com.xiaofeng.strangeloop.system.domain.ModelMessage;
import com.xiaofeng.strangeloop.system.mapper.ModelConversationMapper;
import com.xiaofeng.strangeloop.system.mapper.ModelMessageMapper;
import com.xiaofeng.strangeloop.system.service.ModelConversationService;
import com.xiaofeng.strangeloop.system.service.ModelMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelConversationServiceImpl extends ServiceImpl<ModelConversationMapper, ModelConversation> implements ModelConversationService {

    @Autowired
    ModelConversationMapper modelConversationMapper;

    @Override
    public List<ModelConversation> findAll(ModelConversation modelConversation) {
        List<ModelConversation> modelConversations = modelConversationMapper.selectAll(modelConversation);
        return modelConversations;
    }

    @Override
    public ModelConversation findDetailById(Long id) {
        return modelConversationMapper.getDetailById(id);
    }
}
