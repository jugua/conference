package com.epam.cm.core.properties;

public final class PropertiesNames {

    public static final String ENV_CONFIG = "env.config";

    public static final String WAIT_UTILS_TIMEOUT = "wait.utils.timeout";
    public static final String WAIT_UTILS_INTERVAL = "wait.utils.interval";
    public static final String AWAITILITY_AJAX_TIMEOUT = "ajax.timeout";
    public static final String LOADING_PAGE_TIMEOUT = "loading.page.timeout";

    public static final String SITE_HOST = "site.host";
    public static final String SITE_PORT = "site.port";

    public static final String WEBDRIVER_BASE_URL = "webdriver.base.url"; //
    public static final String IMPLICIT_WAIT = "webdriver.timeouts.implicitlywait"; //
    public static final String WEB_DRIVER = "webdriver.driver"; //

    public static final String PARALLEL_AGENT_NUMBER = "parallel.agent.number"; //
    public static final String PARALLEL_AGENT_TOTAL = "parallel.agent.total"; //
    public static final String PROJECT_STORIES = "project.stories";//
    public static final String SUITES_PATH = "suites.path";
    public static final String STORIES_PATH = "stories.path";
    public static final String MAIL_CATCHER_IP = "mailcatcher.smtp.ip";
    public static final String MAIL_CATCHER_SMTP_PORT = "mailcatcher.smtp.port";
    public static final String MAIL_CATCHER_HTTP_PORT = "mailcatcher.http.port";

    private PropertiesNames() {
    }

}
