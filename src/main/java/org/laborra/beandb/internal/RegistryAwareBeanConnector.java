package org.laborra.beandb.internal;

import org.laborra.beandb.BeanDBConnector;
import org.laborra.beandb.dao.BeanRegistryDAO;

public class RegistryAwareBeanConnector implements BeanDBConnector {
    private final BeanRegistryDAO beanRegistryDAO;
    private final BeanDBConnector delegate;

    public RegistryAwareBeanConnector(BeanRegistryDAO beanRegistryDAO, BeanDBConnector delegate) {
        this.beanRegistryDAO = beanRegistryDAO;
        this.delegate = delegate;
    }

    @Override
    public long create(Object object) {
        final long ret = delegate.create(object);
        beanRegistryDAO.store(ret, object.getClass());
        return ret;
    }

    @Override
    public <T> T read(long id) {
        return delegate.read(id);
    }

    @Override
    public void update(long id, Object object) {
        delegate.update(id, object);
    }

    @Override
    public void delete(long id) {
        delegate.delete(id);
        beanRegistryDAO.remove(id);
    }
}
