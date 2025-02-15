package com.xiaofeng.strangeloop.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaofeng.strangeloop.springframework.DateUtils;
import com.xiaofeng.strangeloop.springframework.UserUtil;
import com.xiaofeng.strangeloop.system.domain.ApiResponse;
import com.xiaofeng.strangeloop.system.domain.ModelAiOnline;
import com.xiaofeng.strangeloop.system.service.ModelAiOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/model/ol/manage")
public class ModelAiOnlineController {
    @Autowired
    private ModelAiOnlineService modelAiOnlineService;

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public ApiResponse uploadFile(@RequestParam("file") MultipartFile file) {
        // 检查文件是否为空
        if (file.isEmpty()) {
            return ApiResponse.error("上传失败，文件为空");
        }

        try {
            // 获取系统自定义的存储路径，可以根据实际需要修改
            String uploadDir = "D:/path/to/custom/directory/";

            // 获取文件原始名称
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            // 定义保存路径
            Path path = Paths.get(uploadDir + fileName);

            // 创建目录（如果不存在）
            Files.createDirectories(path.getParent());

            // 保存文件
            file.transferTo(path.toFile());

            // 返回成功响应
            return ApiResponse.success( path.toString());
        } catch (Exception e) {
            return ApiResponse.error("上传失败：" + e.getMessage());
        }
    }

    /**
     * 获取在线模型列表
     *
     * @param modelAiOnline
     * @return
     */
    @GetMapping
    public ApiResponse getModelAiOnlineList( ModelAiOnline modelAiOnline) {
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
