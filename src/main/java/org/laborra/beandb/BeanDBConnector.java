package org.laborra.beandb;

import java.io.Serializable;

public interface BeanDBConnector {

    void save(Object object);

    <T> T load(Serializable id, Class<T> clazz);
}
