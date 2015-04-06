package org.laborra.beandb.persistence;

public interface Persistence {

    long create();

    Cell get(long coordinate);

    void delete(long coordinate);

}
