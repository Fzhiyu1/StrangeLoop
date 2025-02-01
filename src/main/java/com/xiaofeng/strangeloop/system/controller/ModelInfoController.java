package com.xiaofeng.strangeloop.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaofeng.strangeloop.system.domain.ApiResponse;
import com.xiaofeng.strangeloop.system.domain.ModelFile;
import com.xiaofeng.strangeloop.system.domain.ModelInfo;
import com.xiaofeng.strangeloop.system.service.ModelFileService;
import com.xiaofeng.strangeloop.system.service.ModelInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Wrapper;
import java.util.List;

@RestController
@RequestMapping("/model/manage")
public class ModelInfoController {
    @Autowired
    private ModelInfoService modelInfoService;

    @Autowired
    private ModelFileService modelFileService;

    @GetMapping
    public ApiResponse getModelInfoList(@RequestBody ModelInfo modelInfo) {
        List<ModelInfo> modelInfoList = modelInfoService.getModelInfoList(modelInfo);
//        for (int i = 0; i < modelInfoList.size(); i++) {
//            Integer modelFileId = modelInfoList.get(i).getModelFileId();
//            ModelFile modelFile = modelFileService.getById(modelFileId);
//            ModelInfo modelInfoIndex = modelInfoList.get(i);
//            modelInfoIndex.setModelFile(modelFile);
//            modelInfoList.add(i, modelInfoIndex);
//
//        }
        return ApiResponse.success(modelInfoList);
    }
}
