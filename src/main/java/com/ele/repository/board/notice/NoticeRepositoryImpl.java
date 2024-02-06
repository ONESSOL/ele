package com.ele.repository.board.notice;

import com.ele.config.QueryDslUtil;
import com.ele.domain.board.notice.Notice;
import com.ele.domain.board.notice.QNotice;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.ele.domain.board.notice.QNotice.notice;
import static org.springframework.util.ObjectUtils.isEmpty;

public class NoticeRepositoryImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public NoticeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Notice> findByTitleAndContents(String search, Pageable pageable) {

        List<OrderSpecifier> orders = getOrderSpecifier(pageable);

        List<Notice> results = queryFactory
                .select(notice)
                .from(notice)
                .where(firstTitleContains(search).or(contentsContains(search)))
                .orderBy(orders.toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(notice.count())
                .from(notice)
                .where(titleContains(search), contentsContains(search));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    private BooleanExpression firstTitleContains(String title) {
        if(title == null) {
            title = "";
        }
        return notice.title.contains(title);
    }

    private BooleanExpression titleContains(String title) {
        return StringUtils.hasText(title) ? notice.title.contains(title) : null;
    }

    private BooleanExpression contentsContains(String contents) {
        return StringUtils.hasText(contents) ? notice.contents.contains(contents) : null;
    }

    private List<OrderSpecifier> getOrderSpecifier(Pageable pageable) {

        List<OrderSpecifier> orders = new ArrayList<>();
        if(!isEmpty(pageable.getSort())) {
            for(Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                if(order.getProperty().equals("id")) {
                    OrderSpecifier<?> sortId = QueryDslUtil.getSortedColum(direction, notice, "id");
                    orders.add(sortId);
                }
            }
        }
        return orders;
    }
}




















