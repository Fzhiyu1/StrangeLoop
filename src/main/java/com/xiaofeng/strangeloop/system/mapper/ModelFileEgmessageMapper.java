package com.xiaofeng.strangeloop.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaofeng.strangeloop.system.domain.ModelFileEgmessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ModelFileEgmessageMapper extends BaseMapper<ModelFileEgmessage> {

    @Insert({
            "<script>",
            "INSERT INTO model_file_egmessage (model_file_id, message_id,model_message,user_message) VALUES ",
            "<foreach collection='list' item='item' index='index' separator=','>",
            "(#{item.modelFileId}, #{item.messageId},#{item.modelMessage},#{item.userMessage})",
            "</foreach>",
            "</script>"
    })
     int insertBatchSomeColumn(@Param("list") List<ModelFileEgmessage> modelFileEgmessageList);
}
