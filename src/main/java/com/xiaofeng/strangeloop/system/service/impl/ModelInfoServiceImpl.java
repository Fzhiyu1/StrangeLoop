package com.xiaofeng.strangeloop.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaofeng.strangeloop.system.domain.ModelAiOnline;
import com.xiaofeng.strangeloop.system.domain.ModelFile;
import com.xiaofeng.strangeloop.system.domain.ModelFileEgmessage;
import com.xiaofeng.strangeloop.system.domain.ModelInfo;
import com.xiaofeng.strangeloop.system.mapper.ModelAiOnlineMapper;
import com.xiaofeng.strangeloop.system.mapper.ModelFileEgmessageMapper;
import com.xiaofeng.strangeloop.system.mapper.ModelFileMapper;
import com.xiaofeng.strangeloop.system.mapper.ModelInfoMapper;
import com.xiaofeng.strangeloop.system.service.ModelInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelInfoServiceImpl extends ServiceImpl<ModelInfoMapper, ModelInfo> implements ModelInfoService {
    @Autowired
    private ModelInfoMapper modelInfoMapper;

    @Autowired
    private ModelAiOnlineMapper modelAiOnlineMapper;

    @Autowired
    private ModelFileEgmessageMapper modelFileEgmessageMapper;

    @Autowired
    private ModelFileMapper modelFileMapper;

    @Override
    public List<ModelInfo> getModelInfoList(ModelInfo modelInfo) {
        List<ModelInfo> modelInfoList = modelInfoMapper.selectAll(modelInfo);
        return modelInfoList;
    }

    /**
     * 根据模型ID获取模型详细信息（包含关联的在线AI或本地文件配置）
     * @param id 模型信息表主键ID
     * @return 包含关联数据的完整ModelInfo对象，若不存在返回null
     */
    public ModelInfo getModelInfoById(Integer id) {
        // 1. 根据主键ID查询基础模型信息
        ModelInfo modelInfo = modelInfoMapper.selectById(id);

        // 如果查询结果为空，直接返回null
        if (modelInfo == null) {
            return null;
        }
        // 2. 根据链接类型处理不同关联数据
        else if (modelInfo.getLinkType() == 1) {
            // ========== 处理在线AI类型 ==========
            // 获取关联的在线AI主键ID
            Integer aiolId = modelInfo.getAiolId();

            // 构建在线AI查询条件（注意："aiol_id"需与数据库字段名一致）
            QueryWrapper<ModelAiOnline> modelAiOnlineQueryWrapper = new QueryWrapper<>();
            modelAiOnlineQueryWrapper.eq("aiol_id", aiolId); // 根据主键查询

            // 执行查询并获取关联的在线AI数据
            ModelAiOnline returnAiOnline = modelAiOnlineMapper.selectOne(modelAiOnlineQueryWrapper);

            // 将在线AI数据设置到主模型对象中
            modelInfo.setModelAiOnline(returnAiOnline);

        } else if (modelInfo.getLinkType() == 0) {
            // ========== 处理本地AI类型 ==========
            // 获取关联的模型文件主键ID
            Integer modelFileId = modelInfo.getModelFileId();

            // 构建示例消息查询条件（关联模型文件ID）
            QueryWrapper<ModelFileEgmessage> modelFileEgmessageQueryWrapper = new QueryWrapper<>();
            modelFileEgmessageQueryWrapper.eq("model_file_id", modelFileId);

            // 查询关联的示例消息列表
            List<ModelFileEgmessage> modelFileEgmessages = modelFileEgmessageMapper.selectList(modelFileEgmessageQueryWrapper);

            // 查询模型文件基础信息
            ModelFile modelFile = modelFileMapper.selectById(modelFileId);

            // 将示例消息列表设置到模型文件对象中
            modelFile.setModelFileEgmessageList(modelFileEgmessages);

            // 将完整模型文件数据设置到主模型对象中
            modelInfo.setModelFile(modelFile);
        }

        // 3. 返回完整装配后的模型信息对象
        return modelInfo;
    }
}
