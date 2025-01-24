package com.xiaofeng.strangeloop.system.controller;

import com.xiaofeng.strangeloop.system.domain.ApiResponse;
import com.xiaofeng.strangeloop.system.domain.ModelMessage;
import com.xiaofeng.strangeloop.system.service.ModelConversationService;
import com.xiaofeng.strangeloop.system.service.ModelMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/model/message")
public class ModelMessageController {
    @Autowired
    private ModelMessageService modelMessageService;


    /**
     * 新增一条消息记录
     * @param modelMessage
     * @return
     */
    @PostMapping
    public ApiResponse addMessage(@RequestBody ModelMessage modelMessage) {
        System.out.println(modelMessage);
        boolean isSave = modelMessageService.save(modelMessage);
        if (!isSave||modelMessage.getConversationId()==null) {
            return ApiResponse.error("保存消息失败!");
        }
        return ApiResponse.success(null);
    }

    /**
     * 删除一条或多条消息记录
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public ApiResponse deleteMessage(@PathVariable("ids") Long[] ids) {
        if(ids==null||ids.length==0) {
            ApiResponse.error("ids为空");
        }
        ArrayList<Long> idArrays = new ArrayList<>();
        for (Long id : ids) {
            idArrays.add(id);
        }
        boolean isDelete = modelMessageService.removeByIds(idArrays);
        if (!isDelete) {
            ApiResponse.error("删除失败!");

        }
        return ApiResponse.success(null);
    }

}
