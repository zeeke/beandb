package org.laborra.beandb.query;

import com.google.common.base.Predicate;
import org.apache.commons.beanutils.PropertyUtils;
import org.laborra.beandb.BeanDBException;

import javax.annotation.Nullable;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

public class QueryConditions {

    public static <T> QueryCondition eq(final String fieldName, final T value) {

        return new QueryCondition() {
            @Override
            public <F> Predicate<F> getPredicate() {
                return new Predicate<F>() {
                    @Override
                    public boolean apply(@Nullable F input) {
                        final Object propertyValue = getPropertyValue(input, fieldName);
                        return propertyValue.equals(value);
                    }
                };
            }
        };
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

}
