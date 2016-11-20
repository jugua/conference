package com.epam.cm.core.properties;

import com.epam.cm.core.logger.LoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesController.class);

    private static PropertiesController instance = new PropertiesController();

    private final Properties properties = new Properties();

    private PropertiesController() {
        try {
            loadProperties(System.getProperty(PropertiesNames.ENV_CONFIG));
        } catch (final IOException e) {
            throw new IllegalStateException("Failed to load environment configuration file", e);
        }
    }

    public static String getProperty(final String propertyName) {
        return System.getProperty(propertyName, instance.properties.getProperty(propertyName));
    }

    private void loadProperties(final String resource) throws IOException {
        LOGGER.info("Reading environment properties: {}", resource);
        Class asd = getClass();
        final InputStream inputStream = getClass().getResourceAsStream(resource);
        if (inputStream == null) {
            throw new IOException("Unable to open stream for resource " + resource);
        }
        final Properties props = new Properties();
        props.load(inputStream);
        inputStream.close();
        for (final String propertyName : props.stringPropertyNames()) {
            if (propertyName.startsWith("+")) {
                loadProperties(propertyName.substring(1));
            }
        }
        properties.putAll(props);
    }

}