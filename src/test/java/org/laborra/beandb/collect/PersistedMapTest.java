package org.laborra.beandb.collect;

import org.junit.Before;
import org.junit.Test;
import org.laborra.beandb.BeanDBConnector;
import org.laborra.beandb.test.TestUtils;

import java.util.HashMap;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PersistedMapTest {

    private BeanDBConnector connector;
    private long mapId;

    @Before
    public void setupDB() {
        connector = TestUtils.newInMemoryDBConnector();
        mapId = connector.create(new HashMap<>());
    }

    @Test
    public void empty_values() {
        assertThat(new PersistedMap<>(connector, mapId).values(), is(empty()));
    }

    @Test
    public void string_string_map_single_value() {
        final PersistedMap<String, String> sut = new PersistedMap<>(connector, mapId);
        sut.put("K", "V");

        assertThat(sut.size(), equalTo(1));
        assertThat(sut.get("K"), equalTo("V"));
    }

    @Test
    public void multiple_view() {
        final PersistedMap<String, String> firstMap = new PersistedMap<>(connector, mapId);
        firstMap.put("K", "V");
        final PersistedMap<String, String> sut = new PersistedMap<>(connector, mapId);

        assertThat(sut.size(), equalTo(1));
        assertThat(sut.get("K"), equalTo("V"));
    }
}
