package org.laborra.beandb.persistence;

import com.google.common.collect.Ordering;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class MapPersistence implements Persistence {

    private final Map<Long, Cell> cellMap = new HashMap<>();

    @Override
    public long create() {
        try {
            return Ordering.<Long>natural().max(cellMap.keySet()) + 1;
        } catch (NoSuchElementException ignored) {
            return 1;
        }
    }

    @Override
    public Cell get(long coordinate) {
        final Cell cell = cellMap.get(coordinate);
        if (cell == null) {
            final Cell value = new InMemoryCell();
            cellMap.put(coordinate, value);
            return value;
        }

        return cell;
    }

    @Override
    public void delete(long coordinate) {
        cellMap.remove(coordinate);
    }
}
