package org.laborra.beandb.query;

import com.google.common.collect.Ordering;

public interface QueryOrder {

    Ordering getOrdering();

    /**
     * This is a placeholder to avoid sorting select results. SelectAware implementation
     * must reference this field.
     */
    public static QueryOrder ANY = new QueryOrder() {
        @Override
        public Ordering getOrdering() {
            return null;
        }
    };
}
