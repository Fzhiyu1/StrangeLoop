package com.xiaofeng.strangeloop.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class ModelConversation {
    //使表id手动生成
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;
    private Integer userId;
    private String title;
    private String aiName;
    private List<ModelMessage> modelMessageArrayList;

}
