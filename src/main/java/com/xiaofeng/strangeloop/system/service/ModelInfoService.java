package com.xiaofeng.strangeloop.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaofeng.strangeloop.system.domain.ModelInfo;
import com.xiaofeng.strangeloop.system.mapper.ModelInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface ModelInfoService extends IService<ModelInfo> {


    public List<ModelInfo> getModelInfoList(ModelInfo modelInfo);
}
