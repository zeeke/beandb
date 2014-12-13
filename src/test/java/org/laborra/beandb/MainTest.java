package org.laborra.beandb;

import org.junit.Test;
import org.laborra.beandb.internal.BeanDB;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

public class MainTest {

    @Test
    public void basic() {
        final BeanDB beanDB = new BeanDBBuilder().build();

        final SingleStrBean singleStrBean = new SingleStrBean();
        singleStrBean.setData("test data");
        singleStrBean.setId(1);

        final BeanDBConnector beanDBConnector = beanDB.getConnector();
        beanDBConnector.save(singleStrBean);

        final SingleStrBean retrievedBean = beanDBConnector.load(1, SingleStrBean.class);

        assertThat(retrievedBean, samePropertyValuesAs(singleStrBean));

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
