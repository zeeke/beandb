package org.laborra.beandb;

public class BeanDBException extends RuntimeException {

    public BeanDBException(String message) {
        super(message);
    }

    public BeanDBException(String message, Throwable cause) {
        super(message, cause);
    }

    public static BeanDBException create(Throwable cause, String toFormatException, Object ... args) {
        return new BeanDBException(String.format(toFormatException, args), cause);
    }
}
