package com.ele.config;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimplePath;

import java.util.Objects;

public class QueryDslUtil {

    public static OrderSpecifier<?> getSortedColum(Order order, Path<?> parent, String fieldName) {
        SimplePath<Objects> fieldPath = Expressions.path(Objects.class, parent, fieldName);
        return new OrderSpecifier(order, fieldPath);
    }
}
