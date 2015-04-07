package org.laborra.beandb.query;

import com.google.common.base.Predicate;

class PredicateQueryCondition<T> implements QueryCondition {

    private final Predicate<T> predicate;

    PredicateQueryCondition(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V> Predicate<V> getPredicate() {
        return (Predicate<V>) predicate;
    }
}
