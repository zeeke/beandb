package org.laborra.beandb;

public class ObjectNotFoundException extends BeanDBException {

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotFoundException(Throwable e) {
        super(e);
    }
}
