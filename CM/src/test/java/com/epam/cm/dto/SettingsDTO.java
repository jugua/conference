package com.epam.cm.dto;

/**
 * Created by Lev_Serba on 12/13/2016.
 */
public class SettingsDTO {

    private String oldEmail;
    private String newEmail;
    private String firstName;
    private String lastName;
    private String currentPw;
    private String newPw;
    private String confirmNewPw;

    public String getCurrentPw() {
        return currentPw;
    }

    public void setCurrentPw(String currentPw) {
        this.currentPw = currentPw;
    }

    public String getNewPw() {
        return newPw;
    }

    public void setNewPw(String newPw) {
        this.newPw = newPw;
    }

    public String getConfirmNewPw() {
        return confirmNewPw;
    }

    public void setConfirmNewPw(String confirmNewPw) {
        this.confirmNewPw = confirmNewPw;
    }

    public String getOldEmail() {
        return oldEmail;
    }

    public void setOldEmail(String oldEmail) {
        this.oldEmail = oldEmail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

}
