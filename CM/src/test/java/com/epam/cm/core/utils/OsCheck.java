package com.epam.cm.core.utils;

import java.util.Locale;

public final class OsCheck {
    // cached result of OS detection
    private static OSType detectedOS;

    /**
     * types of Operating Systems
     */

    private OsCheck() {
    }

    public static OSType getOperatingSystemType() {
        if (detectedOS == null) {
            String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) {
                detectedOS = OSType.MacOS;
            } else if (OS.indexOf("win") >= 0) {
                detectedOS = OSType.Windows;
            } else if (OS.indexOf("nux") >= 0) {
                detectedOS = OSType.Linux;
            } else {
                detectedOS = OSType.Other;
            }
        }
        return detectedOS;
    }

    public enum OSType {
        Windows, MacOS, Linux, Other
    }
}
