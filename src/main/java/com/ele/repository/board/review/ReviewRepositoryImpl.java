package com.ele.repository.board.review;

import com.ele.config.QueryDslUtil;
import com.ele.domain.board.review.Review;
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
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.ele.domain.board.review.QReview.review;
import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;
import static org.springframework.util.ObjectUtils.isEmpty;

public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ReviewRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Review> findByTitleAndContents(String search, Pageable pageable) {

        List<OrderSpecifier> orders = getOrderSpecifier(pageable);

        List<Review> results = queryFactory
                .select(review)
                .from(review)
                .where(firstTitleContains(search).or(contentsContains(search)))
                .orderBy(orders.toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(review.count())
                .from(review)
                .where(titleContains(search), contentsContains(search));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    @Override
    public void updateHits(Long id) {

        queryFactory
                .update(review)
                .set(review.hits, review.hits.add(1))
                .where(review.id.eq(id))
                .execute();
    }

    private BooleanExpression firstTitleContains(String title) {
        if(title == null) {
            title = "";
        }
        return review.title.contains(title);
    }

    private BooleanExpression titleContains(String title) {
        return StringUtils.hasText(title) ? review.title.contains(title) : null;
    }

    private BooleanExpression contentsContains(String contents) {
        return StringUtils.hasText(contents) ? review.contents.contains(contents) : null;
    }

    private List<OrderSpecifier> getOrderSpecifier(Pageable pageable) {

        List<OrderSpecifier> orders = new ArrayList<>();
        if(isEmpty(pageable.getSort())) {
            for(Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? ASC : DESC;
                if(order.getProperty().equals("id")) {
                    OrderSpecifier<?> sortId = QueryDslUtil.getSortedColum(direction, review, "id");
                    orders.add(sortId);
                }
            }
        }
        return orders;
    }
}



















