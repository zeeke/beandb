package org.laborra.beandb;

import org.laborra.beandb.internal.BeanDB;
import org.laborra.beandb.internal.DefaultConfiguration;
import org.laborra.beandb.internal.KryoDBConnector;
import org.laborra.beandb.persistence.InMemoryObjectPersistence;
import org.laborra.beandb.persistence.ObjectPersistence;

public class BeanDBBuilder {

    private ObjectPersistence objectPersistence;
    private Configuration configuration;

    public BeanDBBuilder withInMemoryPersistenceAdapter() {
        this.objectPersistence = new InMemoryObjectPersistence();
        return this;
    }

    public BeanDBBuilder withDefaultConfiguration() {
        this.configuration = new DefaultConfiguration();
        return this;
    }

    public BeanDB build() {

        if (objectPersistence == null) {
            withInMemoryPersistenceAdapter();
        }

        if (configuration == null) {
            withDefaultConfiguration();
        }

        return new BeanDB(
                objectPersistence,
                configuration,
                KryoDBConnector.create());
    }
}
