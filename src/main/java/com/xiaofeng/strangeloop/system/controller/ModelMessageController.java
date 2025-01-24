package com.xiaofeng.strangeloop.system.controller;

import com.xiaofeng.strangeloop.system.service.ModelConversationService;
import com.xiaofeng.strangeloop.system.service.ModelMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("model/message")
public class ModelMessageController {
    @Autowired
    private ModelMessageService modelMessageService;

    @Autowired
    private ModelConversationService modelConversationService;

}
