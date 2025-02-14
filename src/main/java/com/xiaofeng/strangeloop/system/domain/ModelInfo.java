package com.xiaofeng.strangeloop.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ModelInfo {
    @TableId(type = IdType.AUTO)
    private Integer modelId;
    private String modelVersion;
    private String modelName;
    private Integer modelFileId;
    private Integer disable;
    private Integer userId;
    private Integer aiolId;
    private Integer linkType;
    private String description;
    @TableField(exist = false)
    private ModelFile modelFile;
    @TableField(exist = false)
    private ModelAiOnline modelAiOnline;
    private String localmodelName;


}
