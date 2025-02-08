package com.xiaofeng.strangeloop.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.sql.Update;

import java.time.LocalDateTime;

@Data
public class ModelAiOnline {
    @TableId(value = "ai_id",type = IdType.INPUT)
    private Integer aiId;
    private String aiApi;
    private String aiToken;
    private Integer aiType;
    private String aiName;
    private Integer timeout;
    private String aiVersion;
    private Integer createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime createdTime;
    private Integer updateBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime updateTime;

}
