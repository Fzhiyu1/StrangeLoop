package com.xiaofeng.strangeloop.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaofeng.strangeloop.system.domain.ModelInfo;
import com.xiaofeng.strangeloop.system.mapper.ModelInfoMapper;
import com.xiaofeng.strangeloop.system.service.ModelInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelInfoServiceImpl extends ServiceImpl<ModelInfoMapper, ModelInfo> implements ModelInfoService {
    @Autowired
    private ModelInfoMapper modelInfoMapper;
    @Override
    public List<ModelInfo> getModelInfoList(ModelInfo modelInfo) {
        List<ModelInfo> modelInfoList = modelInfoMapper.selectAll(modelInfo);
        return modelInfoList;
    }
}
