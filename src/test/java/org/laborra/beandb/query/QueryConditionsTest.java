package org.laborra.beandb.query;

import org.junit.Assert;
import org.junit.Test;
import org.laborra.beandb.test.TestUtils;

public class QueryConditionsTest {

    @Test
    public void eq() {
        final QueryCondition sut = QueryConditions.eq("intField", 0);

        Assert.assertFalse(sut.getPredicate().apply(TestUtils.SampleBean.make(1)));
        Assert.assertTrue(sut.getPredicate().apply(TestUtils.SampleBean.make(0)));
        Assert.assertFalse(sut.getPredicate().apply(TestUtils.SampleBean.make(-1)));
    }

    @Test
    public void gt() {
        final QueryCondition sut = QueryConditions.gt("intField", 0);

        Assert.assertTrue(sut.getPredicate().apply(TestUtils.SampleBean.make(1)));
        Assert.assertFalse(sut.getPredicate().apply(TestUtils.SampleBean.make(0)));
        Assert.assertFalse(sut.getPredicate().apply(TestUtils.SampleBean.make(-1)));
    }

    @Test
    public void lt() {
        final QueryCondition sut = QueryConditions.lt("intField", 0);

        Assert.assertFalse(sut.getPredicate().apply(TestUtils.SampleBean.make(1)));
        Assert.assertFalse(sut.getPredicate().apply(TestUtils.SampleBean.make(0)));
        Assert.assertTrue(sut.getPredicate().apply(TestUtils.SampleBean.make(-1)));
    }

    @Test
    public void get() {
        final QueryCondition sut = QueryConditions.get("intField", 0);

        Assert.assertTrue(sut.getPredicate().apply(TestUtils.SampleBean.make(1)));
        Assert.assertTrue(sut.getPredicate().apply(TestUtils.SampleBean.make(0)));
        Assert.assertFalse(sut.getPredicate().apply(TestUtils.SampleBean.make(-1)));
    }

    @Test
    public void let() {
        final QueryCondition sut = QueryConditions.let("intField", 0);

        Assert.assertFalse(sut.getPredicate().apply(TestUtils.SampleBean.make(1)));
        Assert.assertTrue(sut.getPredicate().apply(TestUtils.SampleBean.make(0)));
        Assert.assertTrue(sut.getPredicate().apply(TestUtils.SampleBean.make(-1)));
    }
}
