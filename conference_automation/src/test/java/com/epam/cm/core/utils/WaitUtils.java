package com.epam.cm.core.utils;

import com.epam.cm.core.logger.LoggerFactory;
import com.epam.cm.core.properties.PropertiesController;
import com.jayway.awaitility.core.ConditionFactory;

import org.slf4j.Logger;

import static com.epam.cm.core.properties.PropertiesNames.WAIT_UTILS_INTERVAL;
import static com.epam.cm.core.properties.PropertiesNames.WAIT_UTILS_TIMEOUT;
import static com.jayway.awaitility.Awaitility.with;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public final class WaitUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(WaitUtils.class);

    private static final long TIMEOUT = Long.parseLong(PropertiesController.getProperty(WAIT_UTILS_TIMEOUT));
    private static final long INTERVAL = Long.parseLong(PropertiesController.getProperty(WAIT_UTILS_INTERVAL));
    private static final String LOG_MESSAGE = "{} (elapsed time {}ms, remaining time {}ms)\n";

    private WaitUtils() {
    }

    public static ConditionFactory doWait() {
        return with()
                .conditionEvaluationListener(condition -> LOGGER.debug(LOG_MESSAGE, condition.getDescription(),
                        condition.getElapsedTimeInMS(), condition.getRemainingTimeInMS()))
                .await().atMost(TIMEOUT, MILLISECONDS).with().pollInterval(INTERVAL, MILLISECONDS);
    }

}