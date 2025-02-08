package com.xiaofeng.strangeloop.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaofeng.strangeloop.springframework.DateUtils;
import com.xiaofeng.strangeloop.springframework.UserUtil;
import com.xiaofeng.strangeloop.system.domain.ApiResponse;
import com.xiaofeng.strangeloop.system.domain.ModelAiOnline;
import com.xiaofeng.strangeloop.system.service.ModelAiOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/model/ol/manage")
public class ModelAiOnlineController {
    @Autowired
    private ModelAiOnlineService modelAiOnlineService;

    /**
     * 获取在线模型列表
     *
     * @param modelAiOnline
     * @return
     */
    @GetMapping
    public ApiResponse getModelAiOnlineList(@RequestBody ModelAiOnline modelAiOnline) {
        QueryWrapper<ModelAiOnline> modelAiOnlineQueryWrapper = new QueryWrapper<>(modelAiOnline);
        List<ModelAiOnline> modelAiOnlineList = modelAiOnlineService.list(modelAiOnlineQueryWrapper);
        return ApiResponse.success(modelAiOnlineList);
    }

    /**
     * 获取在线模型详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ApiResponse getModelAiOnlineById(@PathVariable("id") String id) {
        ModelAiOnline modelAiOnline = modelAiOnlineService.getById(id);
        return ApiResponse.success(modelAiOnline);
    }

    /**
     * 新增在线模型
     *
     * @param modelAiOnline
     * @return
     */
    @PostMapping
    public ApiResponse saveModelAiOnline(@RequestBody ModelAiOnline modelAiOnline) {
//        写入创建人创建时间
        modelAiOnline.setCreatedBy(UserUtil.getCurrentUserId());
        modelAiOnline.setCreatedTime(DateUtils.getCurrentLocalDateTime());
        boolean isSave = modelAiOnlineService.save(modelAiOnline);
        if (!isSave) {
            return ApiResponse.error("新增失败");
        }
        return ApiResponse.success(null);

    }

    /**
     *  删除在线模型
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public ApiResponse deleteModelAiOnline(@PathVariable("ids") Long[] ids) {
        ArrayList<Object> idList = new ArrayList<>();
        for (Long id : ids) {
            idList.add(id);
        }
        boolean isRemove = modelAiOnlineService.removeByIds(idList);
        if (!isRemove) {
            return ApiResponse.error("删除失败");
        }
        return ApiResponse.success(null);
    }

    /**
     * 修改在线模型
     * @param modelAiOnline
     * @return
     */
    @PutMapping
    public ApiResponse updateModelAiOnline(@RequestBody ModelAiOnline modelAiOnline) {
//        写入更新人和更新时间
        modelAiOnline.setUpdateBy(UserUtil.getCurrentUserId());
        modelAiOnline.setUpdateTime(DateUtils.getCurrentLocalDateTime());
        boolean isUpdate = modelAiOnlineService.updateById(modelAiOnline);
        if (!isUpdate) {
            return ApiResponse.error("修改失败");
        }
        return ApiResponse.success(null);
    }

}
