package com.xiaofeng.strangeloop.system.controller;

import com.xiaofeng.strangeloop.system.domain.ApiResponse;
import com.xiaofeng.strangeloop.system.domain.ModelConversation;
import com.xiaofeng.strangeloop.system.domain.PageResult;
import com.xiaofeng.strangeloop.system.service.ModelConversationService;
import com.xiaofeng.strangeloop.system.service.ModelMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/model/message")
public class ModelConversationController {
    @Autowired
    private ModelMessageService modelMessageService;

    @Autowired
    private ModelConversationService modelConversationService;

    //    使用mabatis-plus示例controller list操作
    @GetMapping("/list")
    public ApiResponse<PageResult> getModelConversationList(@RequestBody ModelConversation modelConversation) {
        List<ModelConversation> modelConversationList = modelConversationService.findAll(modelConversation);
        PageResult<ModelConversation> modelConversationPageResult = new PageResult<>(modelConversationList);
        return ApiResponse.success(modelConversationPageResult);
    }

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

    @PostMapping
    public ApiResponse addModelConversation(@RequestBody ModelConversation modelConversation) {
        boolean isSave = modelConversationService.save(modelConversation);
        if (!isSave) {
            return ApiResponse.error("新增失败");
        }
        return ApiResponse.success(null);
    }
}
