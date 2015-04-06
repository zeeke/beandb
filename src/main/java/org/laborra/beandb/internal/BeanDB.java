package org.laborra.beandb.internal;

import org.laborra.beandb.BeanDBConnector;
import org.laborra.beandb.Configuration;
import org.laborra.beandb.query.SelectAware;

public class BeanDB {

    private final Configuration configuration;
    private final BeanDBConnector connector;
    private final SelectAware selectAware;

    public BeanDB(Configuration configuration, BeanDBConnector connector, SelectAware selectAware) {
        this.configuration = configuration;
        this.connector = connector;
        this.selectAware = selectAware;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public BeanDBConnector getConnector() {
        return connector;
    }

    public SelectAware getSelectAware() {
        return selectAware;
    }
}
