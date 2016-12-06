package com.epam.cm.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Lev_Serba on 11/28/2016.
 */
public class MyInfoFieldsDTO {
    private static final String STRINGS_NUMBERS_2001 = "stringsNumbers2001";
    private static final String STRINGS_NUMBERS_3000 = "stringsNumbers3000";
    private static final String STRINGS_NUMBERS_257 = "stringsNumbers257";
    private static final String STRINGS_NUMBERS_400 = "stringsNumbers400";
    private static final String STRINGS_NUMBERS_1001 = "stringsNumbers1001";
    private static final String STRINGS_NUMBERS_1200 = "stringsNumbers1200";
    private static final String STRINGS_NUMBERS_1600 = "stringsNumbers1600";
    private static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA_NUMERIC_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

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
        if(shortBio.equals(STRINGS_NUMBERS_2001)){
            this.shortBio = generateRandomAlphaNumericString(2001);
        }else if(shortBio.equals(STRINGS_NUMBERS_3000)){
            this.shortBio = generateRandomAlphaNumericString(3000);
        }else {
            this.shortBio = shortBio;
        }
        listOfFields.add(this.shortBio);
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        if(jobTitle.equals(STRINGS_NUMBERS_257)){
           this.jobTitle = generateRandomAlphaNumericString(257);
        }else if(jobTitle.equals(STRINGS_NUMBERS_400)){
           this.jobTitle = generateRandomAlphaNumericString(400);
        }else {
            this.jobTitle = jobTitle;
        }
        listOfFields.add(this.jobTitle);
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
        if(pastConferences.equals(STRINGS_NUMBERS_1001)){
            this.pastConferences = generateRandomAlphaNumericString(1001);
        }else if(pastConferences.equals(STRINGS_NUMBERS_1200)){
            this.pastConferences = generateRandomAlphaNumericString(1200);
        }else {
            this.pastConferences = pastConferences;
        }
        listOfFields.add(this.pastConferences);
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
        if(additionalInfo.equals(STRINGS_NUMBERS_1001)){
            this.additionalInfo = generateRandomAlphaNumericString(1001);
        }else if(additionalInfo.equals(STRINGS_NUMBERS_1600)){
            this.additionalInfo = generateRandomAlphaNumericString(1600);
        }else {
            this.additionalInfo = additionalInfo;
        }
        listOfFields.add(this.additionalInfo);
    }

    public List<String> getListOfFields() {
        return listOfFields;
    }

    private String generateRandomAlphaNumericString(int strLength){
        StringBuffer randomString = new StringBuffer();
        for(int i=0; i<strLength; i++){
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
