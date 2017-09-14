package com.epam.cm.core.serenity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epam.cm.core.logger.LoggerFactory;

import net.serenitybdd.core.Serenity;

import org.slf4j.Logger;

public class SessionStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionStorage.class);
    private static final String CASTING_ERROR_MESSAGE = "Could not cast value stored in session with key [%s] to %s";

    private SessionStorage() {
    }

    @SuppressWarnings("unchecked")
    public static void storeObject(final String key, final Object obj) {
        if (obj == null) {
            throw new IllegalStateException("Object could not be null");
        }
        Serenity.getCurrentSession().put(key, obj);
    }

    public static <T> T getObject(final String key, final Class<T> returnType) {
        final Object obj = Serenity.getCurrentSession().get(key);
        T result = null;
        try {
            result = returnType.cast(obj);
        } catch (final ClassCastException e) {
            final String message = String.format(CASTING_ERROR_MESSAGE, key, returnType.getName());
            LOGGER.warn(message, e);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> getListOfObjects(final String key, final Class<T> returnType) {
        final Object obj = Serenity.getCurrentSession().get(key);
        List<T> result = new ArrayList<>();
        try {
            result = (List<T>) obj;
        } catch (final ClassCastException e) {
            final String message = String.format(CASTING_ERROR_MESSAGE, key, returnType.getName());
            LOGGER.warn(message, e);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> getMap(final String key, final Class<T> returnType) {
        final Object obj = Serenity.getCurrentSession().get(key);
        Map<String, T> result = new HashMap<>();
        try {
            result = (Map<String, T>) obj;
        } catch (final ClassCastException e) {
            final String message = String.format(CASTING_ERROR_MESSAGE, key, returnType.getName());
            LOGGER.warn(message, e);
        }
        return result;
    }

    public static String getString(final String key) {
        return getObject(key, String.class);
    }

    public static Integer getInteger(final String key) {
        return getObject(key, Integer.class);
    }

    public static Long getLong(final String key) {
        return getObject(key, Long.class);
    }

    public static Double getDouble(final String key) {
        return getObject(key, Double.class);
    }

    public static List<String> getListOfStrings(final String key) {
        return getListOfObjects(key, String.class);
    }

    public static boolean isKeyPresent(final String key) {
        return Serenity.getCurrentSession().containsKey(key);
    }

    public static void removeObject(final String key) {
        if (isKeyPresent(key)) {
            Serenity.getCurrentSession().remove(key);
        }
    }

    public static void clear() {
        Serenity.getCurrentSession().clear();
    }

}
