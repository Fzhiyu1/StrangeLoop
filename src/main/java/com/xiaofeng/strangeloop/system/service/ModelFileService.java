package com.xiaofeng.strangeloop.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaofeng.strangeloop.system.domain.ModelFile;

public interface ModelFileService extends IService<ModelFile> {

    public boolean updateFileAndEgmessage(ModelFile modelFile);
}
