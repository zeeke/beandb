package org.laborra.beandb.query;

import com.google.common.base.Predicate;
import org.apache.commons.beanutils.PropertyUtils;
import org.laborra.beandb.BeanDBException;

import javax.annotation.Nullable;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

public class QueryConditions {

    public static <T> QueryCondition eq(final String fieldName, final T value) {
        return new PredicateQueryCondition<>(new Predicate<T>() {
            @Override
            public boolean apply(@Nullable T input) {
                final Object propertyValue = getPropertyValue(input, fieldName);
                return propertyValue.equals(value);
            }
        });
    }

    public static <T> QueryCondition gt(final String fieldName, final Comparable<T> value) {
        return new PredicateQueryCondition<>(new Predicate<T>() {
            @Override
            @SuppressWarnings("unchecked")
            public boolean apply(@Nullable T input) {
                final Object propertyValue = getPropertyValue(input, fieldName);
                return value.compareTo((T) propertyValue) < 0;
            }
        });
    }

    public static <T> QueryCondition get(final String fieldName, final Comparable<T> value) {
        return new PredicateQueryCondition<>(new Predicate<T>() {
            @Override
            @SuppressWarnings("unchecked")
            public boolean apply(@Nullable T input) {
                final Object propertyValue = getPropertyValue(input, fieldName);
                return value.compareTo((T) propertyValue) <= 0;
            }
        });
    }

    public static <T> QueryCondition lt(final String fieldName, final Comparable<T> value) {
        return new PredicateQueryCondition<>(new Predicate<T>() {
            @Override
            @SuppressWarnings("unchecked")
            public boolean apply(@Nullable T input) {
                final Object propertyValue = getPropertyValue(input, fieldName);
                return value.compareTo((T) propertyValue) > 0;
            }
        });
    }

    public static <T> QueryCondition let(final String fieldName, final Comparable<T> value) {
        return new PredicateQueryCondition<>(new Predicate<T>() {
            @Override
            @SuppressWarnings("unchecked")
            public boolean apply(@Nullable T input) {
                final Object propertyValue = getPropertyValue(input, fieldName);
                return value.compareTo((T) propertyValue) >= 0;
            }
        });
    }

    private static <F> Object getPropertyValue(F input, String fieldName) {
        final PropertyDescriptor propertyDescriptors;
        try {
            propertyDescriptors = PropertyUtils.getPropertyDescriptor(input, fieldName);
            return propertyDescriptors.getReadMethod().invoke(input);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new BeanDBException(String.format(
                    "Error while reading property %s from bean %s, " +
                            fieldName, input),
                    e);
        }
    }

    private static class PredicateQueryCondition<T> implements QueryCondition {

        private final Predicate<T> predicate;

        private PredicateQueryCondition(Predicate<T> predicate) {
            this.predicate = predicate;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <V> Predicate<V> getPredicate() {
            return (Predicate<V>) predicate;
        }
    }
}
