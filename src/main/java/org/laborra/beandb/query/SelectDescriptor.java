package org.laborra.beandb.query;

public class SelectDescriptor<T> {

    private final Class<T> clazz;
    private final QueryCondition queryCondition;
    private final QueryOrder queryOrder;

    public SelectDescriptor(Class<T> clazz, QueryCondition queryCondition, QueryOrder queryOrder) {
        this.clazz = clazz;
        this.queryCondition = queryCondition;
        this.queryOrder = queryOrder;
    }

    public Class<T> getResultClass() {
        return clazz;
    }

    public QueryCondition getQueryCondition() {
        return queryCondition;
    }

    public QueryOrder getQueryOrder() {
        return queryOrder;
    }

}
