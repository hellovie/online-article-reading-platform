package io.github.hellovie.core.specs;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页规范. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/20 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public abstract class BaseSpecification<T> implements Specification<T> {
    private final List<SearchCriteria> list;

    public BaseSpecification() {
        this.list = new ArrayList<>();
    }

    public void add(SearchCriteria searchCriteria) {
        list.add(searchCriteria);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        for (SearchCriteria criteria : list) {
            String key = criteria.getKey();
            Object value = criteria.getValue();
            Expression expression = root.get(key);

            switch (criteria.getOperation()) {
                case GREATER_THAN:
                    predicates.add(builder.greaterThan(expression, value.toString()));
                    break;
                case LESS_THAN:
                    predicates.add(builder.lessThan(expression, value.toString()));
                    break;
                case GREATER_THAN_EQUAL:
                    predicates.add(builder.greaterThanOrEqualTo(expression, value.toString()));
                    break;
                case LESS_THAN_EQUAL:
                    predicates.add(builder.lessThanOrEqualTo(expression, value.toString()));
                    break;
                case NOT_EQUAL:
                    predicates.add(builder.notEqual(expression, value));
                    break;
                case EQUAL:
                    predicates.add(builder.equal(expression, value));
                    break;
                case MATCH:
                    predicates.add(builder.like(builder.lower(expression), "%" + value.toString().toLowerCase() + "%"));
                    break;
                case MATCH_START:
                    predicates.add(builder.like(builder.lower(expression), value.toString().toLowerCase() + "%"));
                    break;
                case MATCH_END:
                    predicates.add(builder.like(builder.lower(expression), "%" + value.toString().toLowerCase()));
                    break;
                case IN:
                    predicates.add(builder.in(expression).value(value));
                    break;
                case NOT_IN:
                    predicates.add(builder.not(expression).in(value));
                    break;
                default:
                    // TODO: 可以抛出异常
            }
        }
        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
