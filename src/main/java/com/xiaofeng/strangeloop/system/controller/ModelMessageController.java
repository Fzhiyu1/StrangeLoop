package com.xiaofeng.strangeloop.system.controller;

import com.xiaofeng.strangeloop.system.domain.ApiResponse;
import com.xiaofeng.strangeloop.system.domain.ModelMessage;
import com.xiaofeng.strangeloop.system.service.ModelConversationService;
import com.xiaofeng.strangeloop.system.service.ModelMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/model/message")
public class ModelMessageController {
    @Autowired
    private ModelMessageService modelMessageService;



    @PostMapping
    public ApiResponse addMessage(@RequestBody ModelMessage modelMessage) {
        System.out.println(modelMessage);
        boolean isSave = modelMessageService.save(modelMessage);
        if (!isSave||modelMessage.getConversationId()==null) {
            return ApiResponse.error("保存消息失败!");
        }
        return ApiResponse.success(null);
    }

}
