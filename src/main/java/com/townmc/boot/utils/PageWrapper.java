package com.townmc.boot.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageWrapper<T> {
    List<T> content; // 数据内容
    Integer number; // 当前查询页（从0开始）
    Integer numberOfElements; // 当前查询页项目数量
    Long totalElements; // 总项目数量
    Integer totalPages; // 总页数
    Integer size; // 每页大小
    boolean empty; // 是否为空
    boolean first; // 是否为首页
    boolean last; // 是否为尾页
}
