package com.xiaofeng.strangeloop.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaofeng.strangeloop.system.domain.ModelFile;
import com.xiaofeng.strangeloop.system.domain.ModelFileEgmessage;
import com.xiaofeng.strangeloop.system.mapper.ModelFileEgmessageMapper;
import com.xiaofeng.strangeloop.system.mapper.ModelFileMapper;
import com.xiaofeng.strangeloop.system.service.ModelFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ModelFileServiceImpl extends ServiceImpl<ModelFileMapper, ModelFile> implements ModelFileService {

    @Autowired
    private ModelFileMapper modelFileMapper;

    @Autowired
    private ModelFileEgmessageMapper modelFileEgmessageMapper;


    /**
     * 更新模型设置表和模型样例回复表
     * @param modelFile
     * @return boolean 成功返回 true，失败返回 false
     */
    @Transactional
    public boolean updateFileAndEgmessage(ModelFile modelFile) {
        try {
            // 更新modelFile实体
            int updateCount = modelFileMapper.updateById(modelFile);
            if (updateCount == 0) {
                // 如果没有更新到任何记录，返回false
                return false;
            }

            // 获取与modelFile关联的所有ModelFileEgmessage
            List<ModelFileEgmessage> modelFileEgmessages = modelFile.getModelFileEgmessageList();

            // 创建查询条件，查找与当前modelFileId关联的所有ModelFileEgmessage
            LambdaQueryWrapper<ModelFileEgmessage> modelFileEgmessageLambdaQueryWrapper = new LambdaQueryWrapper<>();
            modelFileEgmessageLambdaQueryWrapper.eq(ModelFileEgmessage::getModelFileId, modelFile.getModelFileId());

            // 删除旧的ModelFileEgmessage记录
            int deleteCount = modelFileEgmessageMapper.delete(modelFileEgmessageLambdaQueryWrapper);

            // 如果删除的记录数为0，可能没有找到需要删除的记录，可以继续执行
            if (deleteCount == 0) {
                // 仍然可以继续执行插入操作
            }

            // 如果modelFileEgmessages列表不为空
            if (!modelFileEgmessages.isEmpty()) {
                // 更新每个ModelFileEgmessage的modelFileId属性，保证与modelFile关联
                modelFileEgmessages.forEach(modelFileEgmessage -> modelFileEgmessage.setModelFileId(modelFile.getModelFileId()));

                // 批量插入新的ModelFileEgmessage记录
                int insertCount = modelFileEgmessageMapper.insertBatchSomeColumn(modelFileEgmessages);
                if (insertCount == 0) {
                    // 如果没有插入任何记录，说明插入操作失败
                    return false;
                }
            }

            // 所有操作都成功完成，返回true
            return true;
        } catch (Exception e) {
            // 发生异常时打印错误日志（根据需要，也可以使用日志框架）
            System.err.println("更新模型和模型样例回复表时出错：" + e.getMessage());
            return false;
        }
    }



}
