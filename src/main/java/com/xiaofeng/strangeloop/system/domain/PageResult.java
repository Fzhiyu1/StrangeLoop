package com.xiaofeng.strangeloop.system.domain;

import lombok.Data;

import java.util.List;

/**
 * 返回多条数据
 * 接受一个泛型
 * @param <T>
 */
@Data
public class PageResult<T> {
    private long total; // 总条数
    private List<T> items; // 数据列表

    public PageResult( List<T> items) {

        this.items = items;
        this.total = items.size();
    }

    // Getters 和 Setters
}
