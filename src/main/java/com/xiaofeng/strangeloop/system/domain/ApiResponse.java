package com.xiaofeng.strangeloop.system.domain;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private int code;      // 状态码
    private String message; // 消息
    private T data;         // 数据

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "Success", data);
    }

    public static ApiResponse<Void> error(String message) {
        return new ApiResponse<>(500, message, null);
    }

}
