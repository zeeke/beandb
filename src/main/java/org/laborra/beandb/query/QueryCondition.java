package org.laborra.beandb.query;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public interface QueryCondition {

    <T> Predicate<T> getPredicate();

    public static QueryCondition EMPTY = new QueryCondition() {
        @Override
        public <T> Predicate<T> getPredicate() {
            return Predicates.alwaysTrue();
        }
    };
}
