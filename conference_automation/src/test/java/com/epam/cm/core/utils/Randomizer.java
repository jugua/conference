package com.epam.cm.core.utils;

import java.util.Random;

/**
 * Created by Lev_Serba on 12/26/2016.
 */
public class Randomizer {
    private static final String ALPHA_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA_NUMERIC_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String ALPHA_NUMERIC_CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789%-_";
    public static final String COMA_SEPARATOR = ",";
    public static final String AT = "@";
    public static final String DOT = ".";

    public static String generateRandomAlphaString(int strLength) {
        StringBuffer randomString = new StringBuffer();
        for (int i = 0; i < strLength; i++) {
            int number = getRandomNumber();
            char symbol = ALPHA_LIST.charAt(number);
            randomString.append(symbol);
        }
        return randomString.toString();
    }

    public static String generateRandomAlphaNumericString(int strLength) {
        StringBuffer randomString = new StringBuffer();
        for (int i = 0; i < strLength; i++) {
            int number = getRandomNumber();
            char symbol = ALPHA_NUMERIC_LIST.charAt(number);
            randomString.append(symbol);
        }
        return randomString.toString();
    }

    public static String generateRandomAlphaNumericCharString(int strLength) {
        StringBuffer randomString = new StringBuffer();
        for (int i = 0; i < strLength; i++) {
            int number = getRandomNumber();
            char symbol = ALPHA_NUMERIC_CHAR_LIST.charAt(number);
            randomString.append(symbol);
        }
        return randomString.toString();
    }

    public static int getRandomNumber() {
        int randomInt = 0;
        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(ALPHA_LIST.length());
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }

    public static String generateRandomEmail(String emailTemplate) {
        String[] emailTemplateWithoutSeparator = emailTemplate.split(COMA_SEPARATOR);
        int emailPartBeforeAtSignLength = Integer.parseInt(emailTemplateWithoutSeparator[0]);
        int emailPartAfterAtSignLength = Integer.parseInt(emailTemplateWithoutSeparator[1]);
        int emailPartAfterDotLength = Integer.parseInt(emailTemplateWithoutSeparator[2]);
        String email = Randomizer.generateRandomAlphaNumericCharString(emailPartBeforeAtSignLength)+ AT +
                       Randomizer.generateRandomAlphaNumericString(emailPartAfterAtSignLength)+ DOT +
                       Randomizer.generateRandomAlphaString(emailPartAfterDotLength);
        return email;
    }
}
