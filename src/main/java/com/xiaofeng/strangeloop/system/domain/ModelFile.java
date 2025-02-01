package com.xiaofeng.strangeloop.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ModelFile {
    @TableId(type = IdType.AUTO)
    private Integer modelFileId;
    private Integer mirostat;
    private Double mirostatEat;
    private Double mirostatTau;
    private Integer numCtx;
    private Integer repeatLastN;
    private Double repeatPenalty;
    private Double temperature;
    private Integer seed;
    private String stop;
    private Double tfsZ;
    private Integer numPredict;
    private Double topP;
    private Double minP;
}
