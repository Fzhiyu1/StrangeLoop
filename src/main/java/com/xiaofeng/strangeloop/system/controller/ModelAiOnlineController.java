package com.xiaofeng.strangeloop.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaofeng.strangeloop.system.domain.ApiResponse;
import com.xiaofeng.strangeloop.system.domain.ModelAiOnline;
import com.xiaofeng.strangeloop.system.service.ModelAiOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/model/ol/manage")
public class ModelAiOnlineController {
    @Autowired
    private ModelAiOnlineService modelAiOnlineService;

    /**
     * 获取在线模型列表
     * @param modelAiOnline
     * @return
     */
    @GetMapping
    public ApiResponse getModelAiOnlineList(@RequestBody ModelAiOnline modelAiOnline) {
        QueryWrapper<ModelAiOnline> modelAiOnlineQueryWrapper = new QueryWrapper<>(modelAiOnline);
        List<ModelAiOnline> modelAiOnlineList = modelAiOnlineService.list(modelAiOnlineQueryWrapper);
        return ApiResponse.success(modelAiOnlineList);
    }
}
