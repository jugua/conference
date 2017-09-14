package com.epam.cm.dto;

import com.epam.cm.core.utils.Randomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.epam.cm.dto.UserRegistrationInfoDTO.EMPTY;
import static com.epam.cm.dto.UserRegistrationInfoDTO.NOT_NUMERIC;

/**
 * Created by Lev_Serba on 11/28/2016.
 */
public class MyInfoFieldsDTO {
    private String shortBio;
    private String jobTitle;
    private String company;
    private String pastConferences;
    private String linkedIn;
    private String twitter;
    private String facebook;
    private String blog;
    private String additionalInfo;
    private List<String> listOfFields = new ArrayList();

    public String getShortBio() {
        return shortBio;
    }

    public void setShortBio(String shortBio) {
        String digits = shortBio.replaceAll(NOT_NUMERIC, EMPTY);
        if(digits.length() == 0){
            this.shortBio = shortBio;
            listOfFields.add(this.shortBio);
        }else {
            int shortBioLength = Integer.parseInt(digits);
            this.shortBio = Randomizer.generateRandomAlphaNumericString(shortBioLength);
            listOfFields.add(this.shortBio);
        }
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        String digits = jobTitle.replaceAll(NOT_NUMERIC, EMPTY);
        if(digits.length() == 0){
            this.jobTitle = jobTitle;
            listOfFields.add(this.jobTitle);
        }else {
            int jobtitleLength = Integer.parseInt(digits);
            this.jobTitle = Randomizer.generateRandomAlphaNumericString(jobtitleLength);
            listOfFields.add(this.jobTitle);
        }
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
        listOfFields.add(this.company);
    }

    public String getPastConferences() {
        return pastConferences;
    }

    public void setPastConferences(String pastConferences) {
        String digits = pastConferences.replaceAll(NOT_NUMERIC, EMPTY);
        if(digits.length() == 0){
            this.pastConferences = pastConferences;
            listOfFields.add(this.pastConferences);
        }else {
            int pastConferencesLength = Integer.parseInt(digits);
            this.pastConferences = Randomizer.generateRandomAlphaNumericString(pastConferencesLength);
            listOfFields.add(this.pastConferences);
        }
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
        listOfFields.add(this.linkedIn);
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
        listOfFields.add(this.twitter);
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
        listOfFields.add(this.facebook);
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
        listOfFields.add(this.blog);
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        String digits = additionalInfo.replaceAll(NOT_NUMERIC, EMPTY);
        if(digits.length() == 0){
            this.additionalInfo = additionalInfo;
            listOfFields.add(this.additionalInfo);
        }else {
            int additionalInfoLength = Integer.parseInt(digits);
            this.additionalInfo = Randomizer.generateRandomAlphaNumericString(additionalInfoLength);
            listOfFields.add(this.additionalInfo);
        }
    }
    public List<String> getListOfFields() {
        return listOfFields;
    }
}
