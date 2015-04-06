package org.laborra.beandb;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.laborra.beandb.internal.BeanDB;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

public class MainTest {

    private BeanDBConnector beanDBConnector;

    @Before
    public void setUpDB() {
        BeanDB beanDB = new BeanDBBuilder().build();
        beanDBConnector = beanDB.getConnector();
    }

    @Test
    public void create_and_read() {
        final SingleStrBean singleStrBean = createBean(1);

        final long id = beanDBConnector.create(singleStrBean);

        assertThat(
                (SingleStrBean) beanDBConnector.read(id),
                samePropertyValuesAs(singleStrBean)
        );
    }

    @Test
    public void single_bean_update() {

        final long id = beanDBConnector.create(createBean(1));

        final SingleStrBean bean = createBean(1);
        bean.setData("Fresh data");

        beanDBConnector.update(id, bean);

        final SingleStrBean actual = beanDBConnector.read(id);
        assertThat(
                actual.getData(),
                Matchers.equalTo("Fresh data")
        );
    }

    @Test
    public void multiple_bean_override() {

        beanDBConnector.create(createBean(1));
        final long id2 = beanDBConnector.create(createBean(2));
        beanDBConnector.create(createBean(3));

        final SingleStrBean second = beanDBConnector.read(id2);
        second.setData("Fresh data");

        beanDBConnector.update(id2, second);

        final SingleStrBean actual = beanDBConnector.read(id2);
        assertThat(
                actual.getData(),
                Matchers.equalTo("Fresh data")
        );
    }

    @Test(expected = ObjectNotFoundException.class)
    public void object_not_found() {
        beanDBConnector.read(1);
    }


    private SingleStrBean createBean(Integer id) {
        final SingleStrBean singleStrBean = new SingleStrBean();
        singleStrBean.setData(String.format("test data for [%d]", id));
        return singleStrBean;
    }

    public static class SingleStrBean {
        private String data;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
