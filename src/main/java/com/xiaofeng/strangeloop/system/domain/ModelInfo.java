package com.xiaofeng.strangeloop.system.domain;

import lombok.Data;

@Data
public class ModelInfo {
    private Integer modelId;
    private String modelVersion;
    private String modelName;
    private Integer modelFileId;
    private Integer disable;
    private Integer userId;
    private String aiName;
    private Integer linkType;
    private String description;
    private ModelFile modelFile;

}
