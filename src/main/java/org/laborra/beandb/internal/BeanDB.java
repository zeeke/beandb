package org.laborra.beandb.internal;

import org.laborra.beandb.BeanDBConnector;
import org.laborra.beandb.Configuration;
import org.laborra.beandb.persistence.ObjectPersistence;

public class BeanDB {

    private final ObjectPersistence objectPersistence;
    private final Configuration configuration;
    private final BeanDBConnector connector;

    public BeanDB(ObjectPersistence objectPersistence, Configuration configuration, BeanDBConnector connector) {
        this.objectPersistence = objectPersistence;
        this.configuration = configuration;
        this.connector = connector;
    }

    public ObjectPersistence getObjectPersistence() {
        return objectPersistence;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public BeanDBConnector getConnector() {
        return connector;
    }

}
