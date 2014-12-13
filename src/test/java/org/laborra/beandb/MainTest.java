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
    public void basic_save_and_load() {
        final SingleStrBean singleStrBean = createBean(1);

        beanDBConnector.save(singleStrBean);

        assertThat(
                beanDBConnector.load(1, SingleStrBean.class),
                samePropertyValuesAs(singleStrBean)
        );
    }

    @Test
    public void single_bean_override() {

        beanDBConnector.save(createBean(1));

        final SingleStrBean bean = createBean(1);
        bean.setData("Fresh data");

        beanDBConnector.save(bean);

        assertThat(
                beanDBConnector.load(1, SingleStrBean.class).getData(),
                Matchers.equalTo("Fresh data")
        );
    }

    @Test
    public void multiple_bean_override() {

        beanDBConnector.save(createBean(1));
        beanDBConnector.save(createBean(2));
        beanDBConnector.save(createBean(3));

        final SingleStrBean second = beanDBConnector.load(2, SingleStrBean.class);
        second.setData("Fresh data");

        beanDBConnector.save(second);

        assertThat(
                beanDBConnector.load(2, SingleStrBean.class).getData(),
                Matchers.equalTo("Fresh data")
        );
    }

    @Test(expected = ObjectNotFoundException.class)
    public void object_not_found() {
        beanDBConnector.load(1, SingleStrBean.class);
    }


    private SingleStrBean createBean(Integer id) {
        final SingleStrBean singleStrBean = new SingleStrBean();
        singleStrBean.setData(String.format("test data for [%d]", id));
        singleStrBean.setId(id);
        return singleStrBean;
    }


    public static class SingleStrBean {
        private Integer id;
        private String data;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
