package com.xiaofeng.strangeloop.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaofeng.strangeloop.system.domain.ModelInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ModelInfoMapper extends BaseMapper<ModelInfo> {
    public List<ModelInfo> selectAll(ModelInfo modelInfo);

}
