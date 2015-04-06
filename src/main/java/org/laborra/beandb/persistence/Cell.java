package org.laborra.beandb.persistence;

import java.io.InputStream;
import java.io.OutputStream;

public interface Cell {

    OutputStream getOutputStream();

    InputStream getInputStream();
}
