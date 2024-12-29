package com.xiaofeng.strangeloop.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaofeng.strangeloop.system.domain.ModelMessage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

//使用mybatis-plus示例mapper
@Mapper
public interface ModelMessageMapper extends BaseMapper<ModelMessage> {

}
