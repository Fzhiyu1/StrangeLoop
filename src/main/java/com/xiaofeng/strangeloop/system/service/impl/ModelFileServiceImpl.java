package com.xiaofeng.strangeloop.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaofeng.strangeloop.system.domain.ModelFile;
import com.xiaofeng.strangeloop.system.mapper.ModelFileMapper;
import com.xiaofeng.strangeloop.system.service.ModelFileService;
import org.springframework.stereotype.Service;

@Service
public class ModelFileServiceImpl extends ServiceImpl<ModelFileMapper, ModelFile> implements ModelFileService {
}
