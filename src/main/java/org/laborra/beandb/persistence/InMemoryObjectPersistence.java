package org.laborra.beandb.persistence;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class InMemoryObjectPersistence implements ObjectPersistence {

    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @Override
    public OutputStream getOutputStream() {
        outputStream = new ByteArrayOutputStream();
        return outputStream;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
