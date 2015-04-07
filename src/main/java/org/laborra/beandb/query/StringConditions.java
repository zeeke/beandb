package org.laborra.beandb.query;

import com.google.common.base.Predicate;

import java.util.regex.Pattern;

public class StringConditions {

    public static <T> QueryCondition like(final String fieldName, String sqlPattern) {
        final String javaPattern = sqlPattern.replaceAll("%", ".*").replaceAll("_", ".");
        final Pattern pattern = Pattern.compile(javaPattern);
        return new PredicateQueryCondition<>(new Predicate<T>() {
            @Override
            public boolean apply(T input) {
                return pattern.matcher((String) QueryConditions.getPropertyValue(input, fieldName)).matches();
            }
        });
    }
}
