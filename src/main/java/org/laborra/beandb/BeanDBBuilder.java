package org.laborra.beandb;

import org.laborra.beandb.dao.BeanRegistryDAO;
import org.laborra.beandb.internal.BeanDB;
import org.laborra.beandb.internal.DefaultConfiguration;
import org.laborra.beandb.internal.KryoDBConnector;
import org.laborra.beandb.internal.RegistryAwareBeanConnector;
import org.laborra.beandb.persistence.MapPersistence;
import org.laborra.beandb.persistence.Persistence;
import org.laborra.beandb.query.SelectEngine;

public class BeanDBBuilder {

//    private ObjectPersistence objectPersistence;
    private Configuration configuration;
    private Persistence persistence;

    public BeanDBBuilder withInMemoryPersistenceAdapter() {
//        this.objectPersistence = new InMemoryObjectPersistence();
        this.persistence = new MapPersistence();
        return this;
    }

    public BeanDBBuilder withDefaultConfiguration() {
        this.configuration = new DefaultConfiguration();
        return this;
    }

    public BeanDB build() {

//        if (objectPersistence == null) {
//            withInMemoryPersistenceAdapter();
//        }

        if (persistence == null) {
            withInMemoryPersistenceAdapter();
        }

        if (configuration == null) {
            withDefaultConfiguration();
        }

        final KryoDBConnector beanDbConnector = KryoDBConnector.create(persistence);
        final BeanRegistryDAO beanRegistryDAO = new BeanRegistryDAO(beanDbConnector, 0L);
        final RegistryAwareBeanConnector registryAwareBeanConnector =
                new RegistryAwareBeanConnector(beanRegistryDAO, beanDbConnector);

        final SelectEngine selectAware =
                new SelectEngine(beanRegistryDAO, registryAwareBeanConnector);

        return new BeanDB(
                configuration,
                registryAwareBeanConnector,
                selectAware
        );
    }

}
