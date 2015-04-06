package org.laborra.beandb.query;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.laborra.beandb.BeanDBBuilder;
import org.laborra.beandb.BeanDBConnector;
import org.laborra.beandb.internal.BeanDB;

import java.util.*;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class SelectAcceptanceTest {
    private BeanDBConnector beanDBConnector;

    private SelectAware selectAware;
    private World world;

    @Before
    public void setUpDB() {
        BeanDB beanDB = new BeanDBBuilder().build();
        beanDBConnector = beanDB.getConnector();
        selectAware = beanDB.getSelectAware();
        world = new World();
    }

    @Test
    public void find_all() {
        world.populateLinear(10);
        world.save(beanDBConnector);

        final SelectDescriptor<SampleBean> selectDescriptor = new SelectDescriptor<>(
                SampleBean.class,
                QueryCondition.EMPTY,
                QueryOrder.ANY
        );

        final List<SampleBean> result = Lists.newArrayList(selectAware.select(selectDescriptor));


        assertThat(result.size(), equalTo(10));
        assertThat(result, contains(world.beans.toArray()));
    }

    @Test
    public void find_eq() {
        world.populateLinear(10);
        world.save(beanDBConnector);

        final SelectDescriptor<SampleBean> selectDescriptor = new SelectDescriptor<>(
                SampleBean.class,
                QueryConditions.eq("intField", 4),
                QueryOrder.ANY
        );

        final List<SampleBean> result = Lists.newArrayList(selectAware.select(selectDescriptor));

        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).longField, equalTo(40L));
    }

    public static class SampleBean {
        private int intField;
        private long longField;
        private String stringField;
        private Date dateField;

        public int getIntField() {
            return intField;
        }

        public void setIntField(int intField) {
            this.intField = intField;
        }

        public long getLongField() {
            return longField;
        }

        public void setLongField(long longField) {
            this.longField = longField;
        }

        public String getStringField() {
            return stringField;
        }

        public void setStringField(String stringField) {
            this.stringField = stringField;
        }

        public Date getDateField() {
            return dateField;
        }

        public void setDateField(Date dateField) {
            this.dateField = dateField;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SampleBean)) return false;

            SampleBean that = (SampleBean) o;

            return Objects.equals(getIntField(), that.getIntField()) &&
                    Objects.equals(getLongField(), that.getLongField()) &&
                    Objects.equals(getStringField(), that.getStringField()) &&
                    Objects.equals(getDateField(), that.getDateField());
        }

        @Override
        public int hashCode() {
            return Objects.hash(intField, longField, stringField, dateField);
        }
    }

    public static class World {

        public List<SampleBean> beans = new LinkedList<>();

        public void populateLinear(int n) {

            for (int i=0; i<n; i++) {
                final SampleBean sampleBean = new SampleBean();
                sampleBean.intField = i;
                sampleBean.longField = 10L * i;
                sampleBean.stringField = i + "_text";
                final Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(1000*1000 + i);
                sampleBean.dateField = calendar.getTime();
                beans.add(sampleBean);
            }
        }

        public void save(BeanDBConnector beanDBConnector) {
            for (SampleBean bean : beans) {
                beanDBConnector.create(bean);
            }
        }
    }
}
