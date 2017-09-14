package com.epam.cm.dto;

import com.epam.cm.core.utils.Randomizer;

import static com.epam.cm.dto.UserRegistrationInfoDTO.EMPTY;
import static com.epam.cm.dto.UserRegistrationInfoDTO.NOT_NUMERIC;


public class MyTalksDTO {

    public static final int RAND_STR_LENGTH = 13;
    private String title;
    private String description;
    private String topic;
    private String type;
    private String language;
    private String level;
    private String additionalInfo;
    private String status;
    private String noRejectionReasonErrMessage;


    public String getNoRejectionReasonErrMessage() {
        return noRejectionReasonErrMessage;
    }

    public void setNoRejectionReasonErrMessage(String noRejectionReasonErrMessage) {
        this.noRejectionReasonErrMessage = noRejectionReasonErrMessage;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        String digits = comment.replaceAll(NOT_NUMERIC, EMPTY);
        if (digits.length() == 0)
            this.comment = comment;
        else {
            int commentLength = Integer.parseInt(digits);
            this.comment = Randomizer.generateRandomAlphaNumericString(commentLength);
        }

    }

    private String comment;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String orgComment;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
      this.title = title+Randomizer.generateRandomAlphaString(RAND_STR_LENGTH);
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
