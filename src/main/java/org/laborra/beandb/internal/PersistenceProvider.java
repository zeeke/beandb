package org.laborra.beandb.internal;

import org.laborra.beandb.persistence.InMemoryObjectPersistence;
import org.laborra.beandb.persistence.ObjectPersistence;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public interface PersistenceProvider {

    ObjectPersistence getPersistenceFor(Serializable id, Class<?> clazz);

    public static class DefaultPersistenceProvider implements PersistenceProvider {

        private Map<PersistenceCoordinate, ObjectPersistence> persistenceMap = new HashMap<>();

        @Override
        public ObjectPersistence getPersistenceFor(Serializable id, Class<?> clazz) {
            final PersistenceCoordinate persistenceCoordinate = new PersistenceCoordinate(clazz, id);
            if (!persistenceMap.containsKey(persistenceCoordinate)) {
                persistenceMap.put(persistenceCoordinate, createPersistence());
            }

            return persistenceMap.get(persistenceCoordinate);
        }

        private ObjectPersistence createPersistence() {
            return new InMemoryObjectPersistence();
        }
    }

    public static class PersistenceCoordinate {

        private final Class<?> clazz;
        private final Serializable id;

        public PersistenceCoordinate(Class<?> clazz, Serializable id) {
            this.clazz = clazz;
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PersistenceCoordinate)) return false;

            PersistenceCoordinate that = (PersistenceCoordinate) o;

            return clazz.equals(that.clazz) && !(id != null ? !id.equals(that.id) : that.id != null);
        }

        @Override
        public int hashCode() {
            int result = clazz.hashCode();
            result = 31 * result + (id != null ? id.hashCode() : 0);
            return result;
        }
    }
}
