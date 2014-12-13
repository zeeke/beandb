package org.laborra.beandb.internal;

import org.laborra.beandb.BeanDBException;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface IdentifierResolver {

    Serializable getIdFor(Object object);

    public static class DefaultIdentifierResolver implements IdentifierResolver {

        @Override
        public Serializable getIdFor(Object object) {
            try {
                final Method getId = object.getClass().getMethod("getId");
                if (!Serializable.class.isAssignableFrom(getId.getReturnType())) {
                    throw new BeanDBException("[getId()] method must return a java.io.Serializable type.");
                }
                return (Serializable) getId.invoke(object);
            } catch (NoSuchMethodException e) {
                throw new BeanDBException("Bean should have a [getId()] method.");
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw BeanDBException.create(e, "Error while getting id for [%s]", object);
            }
        }
    }
}
