package com.epam.cm.dto;

import java.util.Random;

/**
 * Created by Lev_Serba on 11/10/2016.
 */
public class UserRegistrationInfoDTO {

    public static final String FIRST_NAME_TEMPLATE_LENGTH_1 = "firstName1";
    public static final String FIRST_NAME_TEMPLATE_LENGTH_56 = "firstName56";
    public static final String FIRST_NAME_TEMPLATE_LENGTH_27 = "firstName27";
    public static final String LAST_NAME_TEMPLATE_LENGTH_1 = "lastName1";
    public static final String LAST_NAME_TEMPLATE_LENGTH_56 = "lastName56";
    public static final String LAST_NAME_TEMPLATE_LENGTH_27 = "lastName27";
    public static final String RAND1_RAND1_TT = "Rand1@Rand1.tt";
    public static final String TEST_RAND36_RAND20_TTTT = "testRand36@Rand20.tttt";
    public static final String TEST_RAND4_RAND13_TTTTTT = "testRand4@Rand13.tttttt";
    public static final String PASSWORD_LNGTH_6 = "password6";
    public static final String PASSWORD_LENGTH_30 = "password30";
    public static final String PASSWORD_LENGTH_16 = "password16";
    private static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA_NUMERIC_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String FIRST_NAME_TEMPLATE_LENGTH_57 = "firstName57";
    private static final String FIRST_NAME_TEMPLATE_LENGTH_93 = "firstName93";
    private static final String LAST_NAME_TEMPLATE_LENGTH_57 = "lastName57";
    private static final String LAST_NAME_TEMPLATE_LENGTH_87 = "lastName87";
    private static final String PASSWORD_5_SPACE = "password5space";
    private static final String PASSWORD_6_SPACE = "password6space";
    private static final String CONFPASSWORD_5_SPACE = "confPassword5space";
    private static final String CONFPASSWORD_6_SPACE = "confPassword6space";
    private static final String PASSWORD_LENGTH_5 = "password5";
    private static final String PASSWORD_LENGTH_31 = "password31";
    private static final String PASSWORD_LENGTH_57 = "password57";
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName.equals(FIRST_NAME_TEMPLATE_LENGTH_1)) {
            this.firstName = generateRandomString(1);
        } else if (firstName.equals(FIRST_NAME_TEMPLATE_LENGTH_56)) {
            this.firstName = generateRandomString(56);
        } else if (firstName.equals(FIRST_NAME_TEMPLATE_LENGTH_27)) {
            this.firstName = generateRandomString(27);
        } else if (firstName.equals(FIRST_NAME_TEMPLATE_LENGTH_57)) {
            this.firstName = generateRandomString(57);
        } else if (firstName.equals(FIRST_NAME_TEMPLATE_LENGTH_93)) {
            this.firstName = generateRandomString(93);
        } else {
            this.firstName = firstName;
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName.equals(LAST_NAME_TEMPLATE_LENGTH_1)) {
            this.lastName = generateRandomString(1);
        } else if (lastName.equals(LAST_NAME_TEMPLATE_LENGTH_56)) {
            this.lastName = generateRandomString(56);
        } else if (lastName.equals(LAST_NAME_TEMPLATE_LENGTH_27)) {
            this.lastName = generateRandomString(27);
        } else if (lastName.equals(LAST_NAME_TEMPLATE_LENGTH_57)) {
            this.lastName = generateRandomString(57);
        } else if (lastName.equals(LAST_NAME_TEMPLATE_LENGTH_87)) {
            this.lastName = generateRandomString(87);
        } else {
            this.lastName = lastName;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email.equals(RAND1_RAND1_TT)) {
            this.email = generateRandomString(1) + "@" + generateRandomString(1) + ".tt";
        } else if (email.equals(TEST_RAND36_RAND20_TTTT)) {
            this.email = "test" + generateRandomString(36) + "@" + generateRandomString(20) + ".tttt";

        } else if (email.equals(TEST_RAND4_RAND13_TTTTTT)) {
            this.email = "test" + generateRandomString(4) + "@" + generateRandomString(13) + ".tttttt";

        } else {
            this.email = email;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.equals(PASSWORD_LNGTH_6)) {
            this.password = generateRandomPassword(6);
        } else if (password.equals(PASSWORD_LENGTH_30)) {
            this.password = generateRandomPassword(30);
        } else if (password.equals(PASSWORD_LENGTH_16)) {
            this.password = generateRandomPassword(16);
        } else if (password.equals(PASSWORD_5_SPACE)) {
            this.password = "     ";
        } else if (password.equals(PASSWORD_6_SPACE)) {
            this.password = "      ";
        } else if (password.equals(PASSWORD_LENGTH_5)) {
            this.password = generateRandomPassword(5);
        } else if (password.equals(PASSWORD_LENGTH_31)) {
            this.password = generateRandomPassword(31);
        } else if (password.equals(PASSWORD_LENGTH_57)) {
            this.password = generateRandomPassword(57);
        } else {
            this.password = password;
        }
    }

    public String getConfirmPassword() {
        return password;
    }

    public void setConfirmPassword(String confirmPassword) {
        if (confirmPassword.equals(CONFPASSWORD_5_SPACE)) {
            this.confirmPassword = "     ";
        } else if (password.equals(CONFPASSWORD_6_SPACE)) {
            this.confirmPassword = "      ";
        } else {
            this.confirmPassword = confirmPassword;
        }
    }

    public String getConfirmPasswordNegativeTest() {
        return confirmPassword;
    }

    private String generateRandomString(int strLength) {
        StringBuffer randomString = new StringBuffer();
        for (int i = 0; i < strLength; i++) {
            int number = getRandomNumber();
            char symbol = CHAR_LIST.charAt(number);
            randomString.append(symbol);
        }
        return randomString.toString();
    }

    private String generateRandomPassword(int strLength) {
        StringBuffer randomString = new StringBuffer();
        for (int i = 0; i < strLength; i++) {
            int number = getRandomNumber();
            char symbol = ALPHA_NUMERIC_LIST.charAt(number);
            randomString.append(symbol);
        }
        return randomString.toString();
    }

    private int getRandomNumber() {
        int randomInt = 0;
        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(CHAR_LIST.length());
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }
}
