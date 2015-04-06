package org.laborra.beandb.test;

import org.laborra.beandb.BeanDBConnector;
import org.laborra.beandb.internal.KryoDBConnector;
import org.laborra.beandb.persistence.MapPersistence;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class TestUtils {

    public static BeanDBConnector newInMemoryDBConnector() {
        return KryoDBConnector.create(new MapPersistence());
    }

    public static class SampleBean {
        private int intField;
        private long longField;
        private String stringField;
        private Date dateField;

        public static SampleBean make(int i) {
            final SampleBean sampleBean = new SampleBean();
            sampleBean.setIntField(i);
            sampleBean.setLongField(10L * i);
            sampleBean.setStringField(i + "_text");
            final Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(1000*1000 + i);
            sampleBean.setDateField(calendar.getTime());
            return sampleBean;
        }

        public int getIntField() {
            return intField;
        }

        public void setIntField(int intField) {
            this.intField = intField;
        }

        public long getLongField() {
            return longField;
        }

        public void setLongField(long longField) {
            this.longField = longField;
        }

        public String getStringField() {
            return stringField;
        }

        public void setStringField(String stringField) {
            this.stringField = stringField;
        }

        public Date getDateField() {
            return dateField;
        }

        public void setDateField(Date dateField) {
            this.dateField = dateField;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SampleBean)) return false;

            SampleBean that = (SampleBean) o;

            return Objects.equals(getIntField(), that.getIntField()) &&
                    Objects.equals(getLongField(), that.getLongField()) &&
                    Objects.equals(getStringField(), that.getStringField()) &&
                    Objects.equals(getDateField(), that.getDateField());
        }

        @Override
        public int hashCode() {
            return Objects.hash(intField, longField, stringField, dateField);
        }
    }
}
