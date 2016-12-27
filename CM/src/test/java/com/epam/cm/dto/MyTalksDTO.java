package com.epam.cm.dto;

import com.epam.cm.core.utils.Randomizer;

import static com.epam.cm.dto.UserRegistrationInfoDTO.EMPTY;
import static com.epam.cm.dto.UserRegistrationInfoDTO.NOT_NUMERIC;

/**
 * Created by Serhii_Kobzar on 12/26/2016.
 */
public class MyTalksDTO {

    private String title;
    private String description;
    private String topic;
    private String type;
    private String language;
    private String level;
    private String additionalInfo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        String digits = title.replaceAll(NOT_NUMERIC, EMPTY);
        if (digits.length() == 0)
            this.title = title;
        else {
            int titleLength = Integer.parseInt(digits);
            this.title = Randomizer.generateRandomAlphaNumericString(titleLength);
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        String digits = description.replaceAll(NOT_NUMERIC, EMPTY);
        if (digits.length() == 0)
            this.description = description;
        else {
            int descriptionLength = Integer.parseInt(digits);
            this.description = Randomizer.generateRandomAlphaNumericString(descriptionLength);
        }
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        String digits = additionalInfo.replaceAll(NOT_NUMERIC, EMPTY);
        if (digits.length() == 0)
            this.additionalInfo = additionalInfo;
        else {
            int additionalInfoLength = Integer.parseInt(digits);
            this.additionalInfo = Randomizer.generateRandomAlphaNumericString(additionalInfoLength);
        }
    }

}
