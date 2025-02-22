package com.xiaofeng.strangeloop.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaofeng.strangeloop.system.domain.*;
import com.xiaofeng.strangeloop.system.service.ModelAiOnlineService;
import com.xiaofeng.strangeloop.system.service.ModelConversationService;
import com.xiaofeng.strangeloop.system.service.ModelInfoService;
import com.xiaofeng.strangeloop.system.service.ModelMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/model/conversation")
public class ModelConversationController {
    @Autowired
    private ModelMessageService modelMessageService;

    @Autowired
    private ModelConversationService modelConversationService;

    @Autowired
    private ModelInfoService modelInfoService;

    @Autowired
    private ModelAiOnlineService modelAiOnlineService;


    //    使用mabatis-plus示例controller list操作

    /**
     * 获取会话列表
     * @param modelConversation
     * @return
     */
    @GetMapping("/list")
    public ApiResponse<PageResult> getModelConversationList(ModelConversation modelConversation) {
        List<ModelConversation> modelConversationList = modelConversationService.findAll(modelConversation);
//        获取modelInfo
        for (ModelConversation conversation : modelConversationList) {
            ModelInfo modelInfo = modelInfoService.getById(conversation.getModelInfoId());
//            根据linkType获取baseModel
            if(modelInfo != null) {
                if (modelInfo.getLinkType() == 1) {
                    ModelAiOnline aiOnline = modelAiOnlineService.getById(modelInfo.getAiolId());
                    modelInfo.setBaseModelName(aiOnline.getAiName());
                }else {
                    modelInfo.setBaseModelName(modelInfo.getLocalmodelName());
                }
            }
            conversation.setModelInfo(modelInfo);
        }
        PageResult<ModelConversation> modelConversationPageResult = new PageResult<>(modelConversationList);
        return ApiResponse.success(modelConversationPageResult);
    }

    /**
     * 删除一条或者多条会话
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public ApiResponse deleteModelConversation(@PathVariable("ids") Long[] ids) {
        ArrayList<Long> idArray = new ArrayList<>();
        for (Long id : ids) {
            idArray.add(id);
        }
        boolean isDelete = modelConversationService.removeByIds(idArray);
        if (!isDelete) {
            return ApiResponse.error("删除失败！");
        }

        return ApiResponse.success(null);
    }

    /**
     *新增一条会话
     * @param modelConversation
     * @return
     */
    @PostMapping
    public ApiResponse addModelConversation(@RequestBody ModelConversation modelConversation) {
        boolean isSave = modelConversationService.save(modelConversation);
        if (!isSave) {
            return ApiResponse.error("新增失败");
        }

        return ApiResponse.success(null);
    }

    /**
     * 修改一条会话
     * @param modelConversation
     * @return
     */
    @PutMapping
    public ApiResponse updateModelConversation(@RequestBody ModelConversation modelConversation) {
        boolean isUpdate = modelConversationService.updateById(modelConversation);
        if (!isUpdate) {
            return ApiResponse.error("修改失败");
        }
        return ApiResponse.success(null);
    }
    @GetMapping("/detail/{id}")
    public ApiResponse getModelConversationDetailById(@PathVariable("id") Long id) {
        ModelConversation modelConversation = modelConversationService.findDetailById(id);
        if(modelConversation == null) {
            return ApiResponse.error("会话不存在");
        }
//       构建查询器
        LambdaQueryWrapper<ModelMessage> modelMessageLambdaQueryWrapper = new LambdaQueryWrapper<>();
        modelMessageLambdaQueryWrapper.eq(ModelMessage::getConversationId, modelConversation.getId());

//        将查找到的数据传入modelConversation
        List<ModelMessage> modelMessageList = modelMessageService.list(modelMessageLambdaQueryWrapper);
        modelConversation.setModelMessageArrayList(modelMessageList);
        return ApiResponse.success(modelConversation);
    }

}
