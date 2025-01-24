package com.xiaofeng.strangeloop.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

//使用mybatis-plus示例实体类
@Data
@TableName("model_message")
public class ModelMessage {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String role;
    private String content;
    private Integer conversationId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime createdTime;
}
