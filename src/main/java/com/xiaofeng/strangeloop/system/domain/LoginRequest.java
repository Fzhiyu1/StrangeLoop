package com.xiaofeng.strangeloop.system.domain;

import lombok.Data;

@Data
public class LoginRequest {
    private String account;
    private String password;
}
