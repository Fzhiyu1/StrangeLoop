package com.xiaofeng.strangeloop.system.domain;

import lombok.Data;

@Data
public class ModelFileEgmessage {
    private Integer messageId;
    private String userMessage;
    private String modelMessage;
    private Integer modelFileId;
}
