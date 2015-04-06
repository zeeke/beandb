package org.laborra.beandb.collect;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import org.laborra.beandb.BeanDBConnector;

import java.util.*;

public class PersistedMap<K, V> implements Map<K, V> {

    private final BeanDBConnector beanDBConnector;
    private final Long persistenceMapId;

    public PersistedMap(BeanDBConnector beanDBConnector, Long persistenceMapId) {
        this.beanDBConnector = beanDBConnector;
        this.persistenceMapId = persistenceMapId;
    }

    private Map<K, Long> getPersistenceMap() {
        return beanDBConnector.read(persistenceMapId);
    }

    @Override
    public int size() {
        return getPersistenceMap().size();
    }

    @Override
    public boolean isEmpty() {
        return getPersistenceMap().isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return getPersistenceMap().containsKey(key);
    }

    @Override
    public boolean containsValue(final Object value) {

        return Iterables.tryFind(getPersistenceMap().values(), new Predicate<Long>() {
            @Override
            @SuppressWarnings("unchecked")
            public boolean apply(Long input) {
                final Object persistedObject = beanDBConnector.read(input);
                return persistedObject.equals(value);
            }
        }).isPresent();
    }

    @Override
    public V get(Object key) {
        return beanDBConnector.read(getPersistenceMap().get(key));
    }

    @Override
    public V put(K key, V value) {
        final Map<K, Long> persistenceMap = getPersistenceMap();
        final long id = beanDBConnector.create(value);
        final Long previousValue = persistenceMap.put(key, id);
        beanDBConnector.update(persistenceMapId, persistenceMap);

        if (previousValue == null) {
            return null;
        }
        return beanDBConnector.read(previousValue);
    }

    @Override
    public V remove(Object key) {
        final Map<K, Long> persistenceMap = getPersistenceMap();
        final Long removeId = persistenceMap.remove(key);
        if (removeId == null) {
            return null;
        }
        final V ret = beanDBConnector.read(removeId);
        beanDBConnector.delete(removeId);
        beanDBConnector.update(persistenceMapId, persistenceMap);
        return ret;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    @Override
    public void clear() {
        final Map<K, Long> persistenceMap = getPersistenceMap();
        for (Long itemId : persistenceMap.values()) {
            beanDBConnector.delete(itemId);
        }
        persistenceMap.clear();
        beanDBConnector.update(persistenceMapId, persistenceMap);
    }

    @Override
    public Set<K> keySet() {
        return getPersistenceMap().keySet();
    }

    @Override
    public Collection<V> values() {
        final Map<K, Long> persistenceMap = getPersistenceMap();
        final Collection<V> ret = new LinkedList<>();
        for (Long itemId : persistenceMap.values()) {
            final V read = beanDBConnector.read(itemId);
            ret.add(read);
        }
        return ret;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        final Map<K, Long> persistenceMap = getPersistenceMap();
        return Maps.transformEntries(persistenceMap, new Maps.EntryTransformer<K, Long, V>() {
            @Override
            public V transformEntry(K key, Long value) {
                return beanDBConnector.read(value);
            }
        }).entrySet();
    }
}
