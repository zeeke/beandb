package org.laborra.beandb.persistence;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class InMemoryCell implements Cell {
    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    @Override
    public OutputStream getOutputStream() {
        byteArrayOutputStream.reset();
        return byteArrayOutputStream;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
}
