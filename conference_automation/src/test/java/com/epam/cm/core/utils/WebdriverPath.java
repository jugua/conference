package com.epam.cm.core.utils;

// import static com.epam.cm.core.properties.PropertiesController.getProperty;

/**
 * Created by Denys_Shmyhin on 11/20/2016.
 */
public final class WebdriverPath {

    private WebdriverPath() {
    }

    public static void bindWebdriverPath() {

        String browser = System.getProperty("webdriver.driver", "firefox");

        switch (browser) {

        case "edge":
            System.setProperty("webdriver.edge.driver", "drivers/edgedriver-windows-64bit.exe");
            break;
        case "iexplorer":
            System.setProperty("webdriver.ie.driver", "drivers/internetexplorerdriver-windows-32bit.exe");
            break;
        case "chrome":
            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver-windows-32bit.exe");
            break;
        default:
        }
    }
}
