package com.xiaofeng.strangeloop.system.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaofeng.strangeloop.system.domain.ModelConversation;
import com.xiaofeng.strangeloop.system.mapper.ModelConversationMapper;

import java.util.List;

public interface ModelConversationService extends IService<ModelConversation> {
    public List<ModelConversation> findAll(ModelConversation modelConversation);
    public ModelConversation findDetailById(Long id);

}
