package com.epam.cm.dto;

import com.epam.cm.core.utils.Randomizer;

public class UserRegistrationInfoDTO {
    public static final String TEMPLATE_5_SPACE = "5space";
    public static final String TEMPLATE_6_SPACE = "6space";
    public static final String FIVE_SPACES = "     ";
    public static final String SIX_SPACES = "      ";
    public static final String NOT_NUMERIC = "[^0-9]";
    public static final String EMPTY = "";
    public static final String EMAIL_TEMPLATE_REGEX = "[0-9]{1,},[0-9]{1,},[0-9]{1,}";
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;

    public void setFirstName(String firstName) {
        String digits = firstName.replaceAll(NOT_NUMERIC, EMPTY);
        if(digits.length() == 0){
            this.firstName = firstName;
        }else {
                int firstNameLength = Integer.parseInt(digits);
                this.firstName = Randomizer.generateRandomAlphaString(firstNameLength);
        }
    }

    public void setLastName(String lastName) {
        String digits = lastName.replaceAll(NOT_NUMERIC, EMPTY);
        if(digits.length() == 0){
            this.lastName = lastName;
        }else {
                int lastNameLength = Integer.parseInt(digits);
                this.lastName = Randomizer.generateRandomAlphaString(lastNameLength);
        }
    }

    public void setEmail(String emailTemplate) {
        String emailTemplateRegex = EMAIL_TEMPLATE_REGEX;
        if(!emailTemplate.matches(emailTemplateRegex)){
            this.email = emailTemplate;
        }else {
                this.email = Randomizer.generateRandomEmail(emailTemplate);
        }
    }

    public void setPassword(String password) {
        String digits = password.replaceAll(NOT_NUMERIC, EMPTY);
        if(digits.length() == 0){
            this.password = password;
        }else {
                if (password.equalsIgnoreCase(TEMPLATE_5_SPACE)) {
                    this.password = FIVE_SPACES;
                } else if (password.equalsIgnoreCase(TEMPLATE_6_SPACE)) {
                    this.password = SIX_SPACES;
                } else {
                    int passwordLength = Integer.parseInt(digits);
                    this.password = Randomizer.generateRandomAlphaNumericString(passwordLength);
                }
        }
    }

    public void setConfirmPassword(String confirmPassword) {
        String digits = confirmPassword.replaceAll(NOT_NUMERIC, EMPTY);
        if(digits.length() == 0){
            this.confirmPassword = confirmPassword;
        }else {
                if (confirmPassword.equalsIgnoreCase(TEMPLATE_5_SPACE)) {
                    this.confirmPassword = FIVE_SPACES;
                } else if (confirmPassword.equalsIgnoreCase(TEMPLATE_6_SPACE)) {
                    this.confirmPassword = SIX_SPACES;
                } else {
                    int confirmPasswordLength = Integer.parseInt(digits);
                    this.confirmPassword = Randomizer.generateRandomAlphaNumericString(confirmPasswordLength);
                }
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getConfirmPasswordNegativeTest() {
        return confirmPassword;
    }

}
