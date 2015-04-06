package org.laborra.beandb.persistence;

import org.laborra.beandb.BeanDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Random;

public class DirectoryPersistence implements Persistence {

    private final static Logger LOG = LoggerFactory.getLogger(org.laborra.beandb.persistence.DirectoryPersistence.class);
    private final File rootDirectory;

    private final Random random = new Random();

    public DirectoryPersistence(File rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    @Override
    public long create() {
        long ret;
        while (true) {
            ret = random.nextLong();
            final File dataFile = getFile(ret);
            dataFile.mkdirs();

            try {
                if (dataFile.createNewFile()) {
                    break;
                }
            } catch (IOException e) {
                throw new BeanDBException(e);
            }
        }

        return ret;
    }

    private File getFile(long coordinate) {
        final String fullFileName = fillStringWithZeros(Long.toHexString(coordinate));
        final String dir1 = fullFileName.substring(0, 1) + fullFileName.substring(1, 2);
        final String dir2 = fullFileName.substring(2, 3) + fullFileName.substring(3, 4);
        final String fileName = fullFileName.substring(4);
        return new File(rootDirectory, dir1 + "/" + dir2 + "/" + fileName);
    }

    private String fillStringWithZeros(String str) {
        // TODO
        return str;
    }

    @Override
    public Cell get(final long coordinate) {
        return new Cell() {
            @Override
            public OutputStream getOutputStream() {
                try {
                    return new FileOutputStream(getFile(coordinate));
                } catch (FileNotFoundException e) {
                    throw new BeanDBException(e);
                }
            }

            @Override
            public InputStream getInputStream() {
                try {
                    return new FileInputStream(getFile(coordinate));
                } catch (FileNotFoundException e) {
                    throw new BeanDBException(e);
                }
            }
        };
    }

    @Override
    public void delete(long coordinate) {
        final File file = getFile(coordinate);
        if (!file.delete()) {
            LOG.warn("Cannot delete file [{}]", file.getAbsolutePath());
        }
    }
}
