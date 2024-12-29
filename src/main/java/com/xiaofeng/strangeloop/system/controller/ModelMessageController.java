package com.xiaofeng.strangeloop.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaofeng.strangeloop.system.domain.ApiResponse;
import com.xiaofeng.strangeloop.system.domain.ModelMessage;
import com.xiaofeng.strangeloop.system.domain.PageResult;
import com.xiaofeng.strangeloop.system.service.ModelMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/model/message")
public class ModelMessageController {
    @Autowired
    private ModelMessageService modelMessageService;

//    使用mabatis-plus示例controller list操作
    @GetMapping("/list")
    public ApiResponse<PageResult> getModelMessageList(@RequestBody ModelMessage modelMessage) {
        QueryWrapper<ModelMessage> modelMessageQueryWrapper = new QueryWrapper<>(modelMessage);
        List<ModelMessage> list = modelMessageService.list(modelMessageQueryWrapper);
        PageResult<ModelMessage> modelMessagePageResult = new PageResult<>(list);
        return ApiResponse.success(modelMessagePageResult);
    }
}
