package com.xiaofeng.strangeloop.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaofeng.strangeloop.system.domain.ModelConversation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ModelConversationMapper extends BaseMapper<ModelConversation> {
    public List<ModelConversation> selectAll(ModelConversation conversation);
    public ModelConversation getDetailById(Long id);
}
