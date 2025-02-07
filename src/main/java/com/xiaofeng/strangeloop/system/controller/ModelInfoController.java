package com.xiaofeng.strangeloop.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaofeng.strangeloop.system.domain.ApiResponse;
import com.xiaofeng.strangeloop.system.domain.ModelAiOnline;
import com.xiaofeng.strangeloop.system.domain.ModelFile;
import com.xiaofeng.strangeloop.system.domain.ModelInfo;
import com.xiaofeng.strangeloop.system.service.ModelAiOnlineService;
import com.xiaofeng.strangeloop.system.service.ModelFileService;
import com.xiaofeng.strangeloop.system.service.ModelInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;

@RestController
@RequestMapping("/model/manage")
public class ModelInfoController {
    @Autowired
    private ModelInfoService modelInfoService;

    @Autowired
    private ModelFileService modelFileService;

    @Autowired
    private ModelAiOnlineService modelAiOnlineService;

    /**
     * 获取模型信息列表
     * @param modelInfo
     * @return
     */
    @GetMapping
    public ApiResponse getModelInfoList(@RequestBody ModelInfo modelInfo) {
        List<ModelInfo> modelInfoList = modelInfoService.getModelInfoList(modelInfo);
        return ApiResponse.success(modelInfoList);
    }

    /**
     * 获取模型详细信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ApiResponse  getModelInfoById(@PathVariable("id") Integer id) {
        ModelInfo modelInfo = modelInfoService.getById(id);
//        根据linkType判断连接类型
        if (modelInfo.getLinkType() == 1) {
//            当linkType==1时为在线ai
//            获取在线ai信息
            String aiName = modelInfo.getAiName();
            ModelAiOnline modelAiOnline = new ModelAiOnline();
            modelAiOnline.setAiName(aiName);
            QueryWrapper<ModelAiOnline> modelAiOnlineQueryWrapper = new QueryWrapper<>(modelAiOnline);
            ModelAiOnline returnAiOnline = modelAiOnlineService.getOne(modelAiOnlineQueryWrapper);
            modelInfo.setModelAiOnline(returnAiOnline);
        } else if (modelInfo.getLinkType() == 0) {
//            当linkType==0时为本地ai
            Integer modelFileId = modelInfo.getModelFileId();
//            获取ollama本地ai设置信息
            ModelFile modelFile = modelFileService.getById(modelFileId);
            modelInfo.setModelFile(modelFile);

        }
//        返回数据
        return ApiResponse.success(modelInfo);
    }
}
