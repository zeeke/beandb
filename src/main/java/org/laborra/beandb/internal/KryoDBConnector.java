package org.laborra.beandb.internal;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.laborra.beandb.BeanDBConnector;
import org.laborra.beandb.persistence.ObjectPersistence;

import java.io.Serializable;

public class KryoDBConnector implements BeanDBConnector {

    private final Kryo kryo;
    private final IdentifierResolver identifierResolver;
    private final PersistenceProvider persistenceProvider;

    private KryoDBConnector(Kryo kryo, IdentifierResolver identifierResolver, PersistenceProvider persistenceProvider) {
        this.kryo = kryo;
        this.identifierResolver = identifierResolver;
        this.persistenceProvider = persistenceProvider;
    }

    public static KryoDBConnector create() {
        return new KryoDBConnector(
                new Kryo(),
                new IdentifierResolver.DefaultIdentifierResolver(),
                new PersistenceProvider.DefaultPersistenceProvider()
        );
    }

    @Override
    public void save(Object object) {

        final Serializable objectId = identifierResolver.getIdFor(object);
        final ObjectPersistence objectPersistence = persistenceProvider.getPersistenceFor(objectId, object.getClass());

        try (Output output = new Output(objectPersistence.getOutputStream())) {
            kryo.writeObject(output, object);
        }
    }

    @Override
    public <T> T load(Serializable id, Class<T> clazz) {
        final ObjectPersistence objectPersistence = persistenceProvider.getPersistenceFor(id, clazz);

        try (Input input = new Input(objectPersistence.getInputStream())) {
            return kryo.readObject(input, clazz);
        }
    }
}
