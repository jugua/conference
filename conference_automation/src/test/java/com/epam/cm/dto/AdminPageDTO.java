package com.epam.cm.dto;

/**
 * Created by Serhii_Kobzar on 2/9/2017.
 */
public class AdminPageDTO extends UserRegistrationInfoDTO{
    private String role;
    private String confirmPassword;

    public String getNewConfirmPassword() {
        return confirmPassword;
    }


    public void setNewConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
