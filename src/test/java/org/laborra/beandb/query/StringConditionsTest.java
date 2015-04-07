package org.laborra.beandb.query;

import com.google.common.base.Predicate;
import org.junit.Test;
import org.laborra.beandb.test.TestUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringConditionsTest {

    @Test
    public void like_without_wildcards() {
        final Predicate<Object> predicate = StringConditions.like("stringField", "something").getPredicate();

        assertTrue(predicate.apply(make("something")));
        assertFalse(predicate.apply(make("else")));
    }

    @Test
    public void like_percentage() {
        final Predicate<Object> predicate = StringConditions.like("stringField", "some%thing").getPredicate();

        assertTrue(predicate.apply(make("someXXXthing")));
        assertFalse(predicate.apply(make("other")));
    }

    @Test
    public void like_underscore() {
        final Predicate<Object> predicate = StringConditions.like("stringField", "some_thing").getPredicate();

        assertTrue(predicate.apply(make("someXthing")));
        assertFalse(predicate.apply(make("someXXXthing")));
        assertFalse(predicate.apply(make("other")));
    }

    private static TestUtils.SampleBean make(String fieldValue) {
        final TestUtils.SampleBean ret = new TestUtils.SampleBean();
        ret.setStringField(fieldValue);
        return ret;
    }
}
