package com.xiaofeng.strangeloop.system.domain;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private long total; // 总条数
    private List<T> items; // 数据列表

    public PageResult(long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    // Getters 和 Setters
}
