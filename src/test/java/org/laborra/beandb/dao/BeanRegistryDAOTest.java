package org.laborra.beandb.dao;

import org.junit.Assert;
import org.junit.Test;
import org.laborra.beandb.BeanDBConnector;
import org.laborra.beandb.test.TestUtils;

import static org.hamcrest.Matchers.contains;

public class BeanRegistryDAOTest {
    private BeanDBConnector beanDBConnector = TestUtils.newInMemoryDBConnector();

    @Test
    public void basic() {

        final BeanRegistryDAO beanRegistryDAO = new BeanRegistryDAO(beanDBConnector, 0);

        beanRegistryDAO.store(42, String.class);
        beanRegistryDAO.store(10, String.class);

        final BeanRegistryDAO.IdList result = beanRegistryDAO.getAll(String.class);

        Assert.assertThat(result, contains(42L, 10L));
    }
}
