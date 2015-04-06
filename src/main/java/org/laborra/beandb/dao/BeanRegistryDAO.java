package org.laborra.beandb.dao;

import org.laborra.beandb.BeanDBConnector;
import org.laborra.beandb.ObjectNotFoundException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BeanRegistryDAO {

    private final BeanDBConnector beanDBConnector;
    private final Long mainId;

    public BeanRegistryDAO(BeanDBConnector beanDBConnector, long mainId) {
        this.beanDBConnector = beanDBConnector;
        this.mainId = mainId;
    }

    public void store(long id, final Class<?> clazz) {
        final KeyToListIdMap keyToIdList = lazyLoadKeyToIdMap();

        if (keyToIdList.containsKey(clazz)) {
            final Long idListIdentifier = keyToIdList.get(clazz);
            final IdList idList = beanDBConnector.read(idListIdentifier);
            idList.add(id);
            beanDBConnector.update(idListIdentifier, idList);
        } else {
            final IdList idList = new IdList();
            idList.add(id);
            final long idListIdentifier = beanDBConnector.create(idList);
//                beanDBConnector.update(idListIdentifier, idList);
            keyToIdList.put(clazz, idListIdentifier);
            beanDBConnector.update(mainId, keyToIdList);
        }
    }

    public void remove(long id) {
        final KeyToListIdMap keyToIdList = lazyLoadKeyToIdMap();

        for (Map.Entry<Serializable, Long> entry : keyToIdList.entrySet()) {
            final IdList idList = beanDBConnector.read(entry.getValue());

            for (Long storedId : idList) {
                if (storedId == id) {
                    idList.remove(id);
                    beanDBConnector.update(entry.getValue(), idList);
                    return;
                }
            }
        }
    }

    public IdList getAll(Class<?> clazz) {
        final KeyToListIdMap keyToIdList = lazyLoadKeyToIdMap();

        final Long idListIdentifier = keyToIdList.get(clazz);
        if (keyToIdList.containsKey(clazz)) {
            return beanDBConnector.read(idListIdentifier);
        }

        return new IdList();
    }

    private KeyToListIdMap lazyLoadKeyToIdMap() {
        try {
            return beanDBConnector.read(mainId);
        } catch (ObjectNotFoundException e) {
            // First time there is no map. It is not the cleanest solution.
            return new KeyToListIdMap();
        }
    }

    public static class KeyToListIdMap extends HashMap<Serializable, Long> {

    }

    public static class IdList extends ArrayList<Long> {

    }

}
