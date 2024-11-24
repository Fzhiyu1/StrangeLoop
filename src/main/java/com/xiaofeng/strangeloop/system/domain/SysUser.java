package com.xiaofeng.strangeloop.system.domain;

import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * 用户类
 */
@Data
public class SysUser {
    private Integer id;
    private String account;
    private String email;
    private String phone;
    private Integer disable;
    private Integer role;
    private String name;
    private String password;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
