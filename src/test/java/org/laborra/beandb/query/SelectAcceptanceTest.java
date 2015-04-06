package org.laborra.beandb.query;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.laborra.beandb.BeanDBBuilder;
import org.laborra.beandb.BeanDBConnector;
import org.laborra.beandb.internal.BeanDB;
import org.laborra.beandb.test.TestUtils;

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

        final SelectDescriptor<TestUtils.SampleBean> selectDescriptor = new SelectDescriptor<>(
                TestUtils.SampleBean.class,
                QueryCondition.EMPTY,
                QueryOrder.ANY
        );

        final List<TestUtils.SampleBean> result = Lists.newArrayList(selectAware.select(selectDescriptor));


        assertThat(result.size(), equalTo(10));
        assertThat(result, contains(world.beans.toArray()));
    }

    @Test
    public void find_eq() {
        world.populateLinear(10);
        world.save(beanDBConnector);

        final SelectDescriptor<TestUtils.SampleBean> selectDescriptor = new SelectDescriptor<>(
                TestUtils.SampleBean.class,
                QueryConditions.eq("intField", 4),
                QueryOrder.ANY
        );

        final List<TestUtils.SampleBean> result = Lists.newArrayList(selectAware.select(selectDescriptor));

        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getLongField(), equalTo(40L));
    }

    @Test
    public void find_int_gt() {
        world.populateLinear(10);
        world.save(beanDBConnector);

        final SelectDescriptor<TestUtils.SampleBean> selectDescriptor = new SelectDescriptor<>(
                TestUtils.SampleBean.class,
                QueryConditions.gt("intField", 4),
                QueryOrder.ANY
        );

        final List<TestUtils.SampleBean> result = Lists.newArrayList(selectAware.select(selectDescriptor));

        assertThat(result.size(), equalTo(5));
        assertThat(
                result,
                contains(
                        world.beans.get(5),
                        world.beans.get(6),
                        world.beans.get(7),
                        world.beans.get(8),
                        world.beans.get(9)
                )
        );
    }

    public static class World {

        public List<TestUtils.SampleBean> beans = new LinkedList<>();

        public void populateLinear(int n) {

            for (int i=0; i<n; i++) {
                final TestUtils.SampleBean sampleBean = TestUtils.SampleBean.make(i);
                beans.add(sampleBean);
            }
        }

        public void save(BeanDBConnector beanDBConnector) {
            for (TestUtils.SampleBean bean : beans) {
                beanDBConnector.create(bean);
            }
        }
    }
}
