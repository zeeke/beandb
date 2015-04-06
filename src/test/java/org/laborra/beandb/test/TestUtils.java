package org.laborra.beandb.test;

import org.laborra.beandb.BeanDBConnector;
import org.laborra.beandb.internal.KryoDBConnector;
import org.laborra.beandb.persistence.MapPersistence;

public class TestUtils {

    public static BeanDBConnector newInMemoryDBConnector() {
        return KryoDBConnector.create(new MapPersistence());
    }
}
