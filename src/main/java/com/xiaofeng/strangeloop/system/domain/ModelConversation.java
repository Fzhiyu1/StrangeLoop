package com.xiaofeng.strangeloop.system.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;

@Data
public class ModelConversation {
    private Integer id;
    private Integer userId;
    private String title;
    private ArrayList<ModelMessage> modelMessageArrayList;

}
