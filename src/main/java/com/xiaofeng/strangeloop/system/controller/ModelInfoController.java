package com.xiaofeng.strangeloop.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaofeng.strangeloop.springframework.UserUtil;
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
            Integer aiolId = modelInfo.getAiolId();
            ModelAiOnline modelAiOnline = new ModelAiOnline();
            modelAiOnline.setAiId(aiolId);
            QueryWrapper<ModelAiOnline> modelAiOnlineQueryWrapper = new QueryWrapper<>();
            modelAiOnlineQueryWrapper.eq("aiol_id", aiolId);
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


        return ApiResponse.success(null);
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

    @DeleteMapping("/{ids}")
    public ApiResponse deleteModelInfo(@PathVariable("ids") Long[] ids) {
        ArrayList<Long> idArray = new ArrayList<>();
        for (Long id : ids) {
            idArray.add(id);
        }
        boolean isRemove = modelInfoService.removeByIds(idArray);
        if (!isRemove) {
            return ApiResponse.error("删除失败！");
        }
        return ApiResponse.success(null);
    }
}
