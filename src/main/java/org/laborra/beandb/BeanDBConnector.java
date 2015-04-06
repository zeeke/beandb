package org.laborra.beandb;

public interface BeanDBConnector {

    long create(Object object);

    <T> T read(long id);

    void update(long id, Object object);

    void delete(long id);
}
