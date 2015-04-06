package org.laborra.beandb.query;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import org.laborra.beandb.BeanDBConnector;
import org.laborra.beandb.dao.BeanRegistryDAO;

import javax.annotation.Nullable;

public class SelectEngine implements SelectAware {

    private final BeanRegistryDAO beanRegistryDAO;
    private final BeanDBConnector beanDBConnector;

    public SelectEngine(BeanRegistryDAO beanRegistryDAO, BeanDBConnector beanDBConnector) {
        this.beanRegistryDAO = beanRegistryDAO;
        this.beanDBConnector = beanDBConnector;
    }

    @Override
    public <T> Iterable<T> select(final SelectDescriptor<T> selectDescriptor) {

        final Iterable<Long> idsForClass = beanRegistryDAO.getAll(selectDescriptor.getResultClass());

        final Iterable<T> allBeans = Iterables.transform(idsForClass, new Function<Long, T>() {
            @Nullable
            @Override
            public T apply(Long input) {
                return beanDBConnector.read(input);
            }
        });


        final Iterable<T> filteredBeans =
                Iterables.filter(allBeans, selectDescriptor.getQueryCondition().getPredicate());

        final QueryOrder queryOrder = selectDescriptor.getQueryOrder();
        if (queryOrder != QueryOrder.ANY) {
            return queryOrder.getOrdering().immutableSortedCopy(filteredBeans);
        }

        return filteredBeans;
    }
}
