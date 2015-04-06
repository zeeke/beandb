package org.laborra.beandb.internal;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.laborra.beandb.BeanDBConnector;
import org.laborra.beandb.ObjectNotFoundException;
import org.laborra.beandb.persistence.Cell;
import org.laborra.beandb.persistence.Persistence;

public class KryoDBConnector implements BeanDBConnector {

    private final Kryo kryo;
    private final Persistence persistence;

    private KryoDBConnector(Kryo kryo, Persistence persistence) {
        this.kryo = kryo;
        this.persistence = persistence;
    }

    public static KryoDBConnector create(Persistence persistence) {
        return new KryoDBConnector(
                new Kryo(),
                persistence
        );
    }

    @Override
    public long create(Object object) {

        final long coordinate = persistence.create();
        final Cell cell = persistence.get(coordinate);
        try (Output output = new Output(cell.getOutputStream())) {
            kryo.writeClassAndObject(output, object);
        }
        return coordinate;
    }

    @Override
    public <T> T read(long id) {

        final Cell cell = persistence.get(id);
        if (cell == null) {
            throw new ObjectNotFoundException("Cannot find object with identifier " + id);
        }

        try (Input input = new Input(cell.getInputStream())) {
            @SuppressWarnings("unchecked")
            final T ret = (T) kryo.readClassAndObject(input);
            return ret;
        } catch (KryoException e) {
            throw new ObjectNotFoundException(e);
        }
    }

    @Override
    public void update(long id, Object object) {
        final Cell cell = persistence.get(id);
        if (cell == null) {
            throw new ObjectNotFoundException("Cannot update object with identifier " + id);
        }

        try (Output output = new Output(cell.getOutputStream())) {
            kryo.writeClassAndObject(output, object);
        }
    }

    @Override
    public void delete(long id) {
        persistence.delete(id);
    }
}
