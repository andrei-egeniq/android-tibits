package com.egeniq.state.app;

import android.content.Context;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by andrei on 24/11/16.
 */

public class StateCache {
    private static String CACHE_FILE_NAME = "state_cache";
    private static ConcurrentHashMap<Class<?>, Serializable> mCacheHashMap = new ConcurrentHashMap<>();

    /**
     * Read cache from disk
     */
    static void read(Context context) throws IOException {
        File file = new File(context.getCacheDir(), CACHE_FILE_NAME);
        if(!file.exists()) {
            return;
        }
        InputStream fileStream = new FileInputStream(file);
        DataInputStream dataStream = new DataInputStream(fileStream);

        byte[] bytes = new byte[(int)file.length()];
        dataStream.readFully(bytes);

        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectStream = new ObjectInputStream(byteStream);

        try {
            mCacheHashMap = (ConcurrentHashMap<Class<?>, Serializable>)objectStream.readObject();
        } catch (ClassNotFoundException e) {
            //should never occur, serialization and deserialization happen in the same code
        }

        objectStream.close();
        byteStream.close();

        dataStream.close();
        fileStream.close();
    }

    /**
     * Write cache to disk
     */
    static void write(Context context) throws IOException {
        OutputStream fileStream = new FileOutputStream(new File(context.getCacheDir(), CACHE_FILE_NAME));
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(512);
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);

        objectStream.writeObject(mCacheHashMap);
        fileStream.write(byteStream.toByteArray());

        objectStream.close();
        byteStream.close();
        fileStream.close();
    }

    /**
     * Write and override in cache
     */
    public static void push(Serializable object) {
        synchronized (mCacheHashMap) {
            mCacheHashMap.put(object.getClass(), object);
        }
    }

    /**
     * Read and remove from cache
     */
    public static <T> T pop(Class<T> type) {
        synchronized (mCacheHashMap) {
            return (T)mCacheHashMap.remove(type);
        }
    }

    public static void clear() {
        synchronized (mCacheHashMap) {
            mCacheHashMap.clear();
        }
    }
}
