package org.laborra.beandb.query;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import org.laborra.beandb.BeanDBBuilder;
import org.laborra.beandb.BeanDBConnector;

import javax.annotation.Nullable;

public interface SelectAware {

    <T> Iterable<T> select(SelectDescriptor<T> selectDescriptor);

}
