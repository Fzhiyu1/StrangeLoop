package com.xiaofeng.strangeloop.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaofeng.strangeloop.springframework.UserUtil;
import com.xiaofeng.strangeloop.system.domain.*;
import com.xiaofeng.strangeloop.system.service.ModelAiOnlineService;
import com.xiaofeng.strangeloop.system.service.ModelFileEgmessageService;
import com.xiaofeng.strangeloop.system.service.ModelFileService;
import com.xiaofeng.strangeloop.system.service.ModelInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.ArrayList;
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

    @Autowired
    private ModelFileEgmessageService modelFileEgmessageService;



    /**
     * 获取模型信息列表
     * @param modelInfo
     * @return
     */
    @GetMapping
    public ApiResponse getModelInfoList(ModelInfo modelInfo) {
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
        ModelInfo modelInfo = modelInfoService.getModelInfoById(id);
//        返回数据
        return ApiResponse.success(modelInfo);
    }

    /**
     * 新建模型信息
     * @param modelInfo
     * @return
     */
    @PostMapping
    public ApiResponse saveModelInfo(@RequestBody ModelInfo modelInfo) {
        if(modelInfo.getLinkType() == null){
            return ApiResponse.error("请填写连接类型!");
        }
//        设置modelInfo的初始值
        ModelFile modelFile = new ModelFile();
        modelInfo.setDisable(1);
        Integer currentUserId = UserUtil.getCurrentUserId();
        modelInfo.setUserId(currentUserId);
//        创建对应的modelFile表
        modelFileService.save(modelFile);
//        获取创建后的File表id
        modelInfo.setModelFileId(modelFile.getModelFileId());
        modelInfoService.save(modelInfo);


        return ApiResponse.success(modelInfo);
    }

    /**
     * 修改模型信息
     * @param modelInfo
     * @return
     */
    @PutMapping
    public ApiResponse updateModelInfo(@RequestBody ModelInfo modelInfo) {
        modelInfoService.updateById(modelInfo);
        return ApiResponse.success(null);
    }

    /**
     * 删除模型信息
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public ApiResponse deleteModelInfo(@PathVariable("ids") Long[] ids) {

//        循环删除
        for (Long id : ids) {
//            获取modelFileId
            ModelInfo modelInfo = modelInfoService.getById(id);
            Integer modelFileId = modelInfo.getModelFileId();
//            根据modelFileId删除
            boolean isRemoveModelFile = modelFileService.removeById(modelFileId);
//            删除失败则返回错错误信息
            if (!isRemoveModelFile) {
                return ApiResponse.error("删除失败");
            }
//            根据传入的id删除
            boolean isRemoveModelInfo = modelInfoService.removeById(id);
//            删除失败则返回错错误信息
            if (!isRemoveModelInfo) {
                return ApiResponse.error("删除失败");
            }

        }
        return ApiResponse.success(null);
    }

    /**
     * 修改模型信息和模型设置接口
     * @param modelInfo
     * @return
     */
    @PutMapping("/detail")
    public ApiResponse updateModelInfoAndModelFile(@RequestBody ModelInfo modelInfo) {
        boolean isUpdateModelInfo = modelInfoService.updateById(modelInfo);
        if (!isUpdateModelInfo) return ApiResponse.error("修改模型信息表失败");
        if (modelInfo.getLinkType() == 1) {
            boolean isUpdateModelAiOnline = modelAiOnlineService.updateById(modelInfo.getModelAiOnline());
            if (!isUpdateModelAiOnline) {
                return ApiResponse.error("修改在线模型设置表失败");
            }
        }
        System.out.println(modelInfo.getModelFile());
        boolean isUpdateModelFile = modelFileService.updateFileAndEgmessage(modelInfo.getModelFile());
        if(!isUpdateModelFile) return ApiResponse.error("修改本地模型设置表失败");
        return ApiResponse.success(null);
    }


}
