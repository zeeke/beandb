package org.laborra.beandb.persistence;

import java.io.InputStream;
import java.io.OutputStream;

public interface ObjectPersistence {

    OutputStream getOutputStream();

    InputStream getInputStream();
}
