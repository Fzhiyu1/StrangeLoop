package com.xiaofeng.strangeloop.system.domain;

import lombok.Data;

@Data
public class SysUser {
    private Integer id;
    private String account;
    private String email;
    private String phone;
    private Integer disable;
    private Integer role;
    private String name;
}
