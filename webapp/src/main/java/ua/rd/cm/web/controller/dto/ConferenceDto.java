package ua.rd.cm.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Created by Anastasiia_Milinchuk on 2/8/2017.
 */
//@Data
@NoArgsConstructor
public class ConferenceDto {
    private Long id;

    @NotNull
    @JsonProperty("title")
    @Size(max = 2000)
    private String title;

    @JsonProperty("description")
    @Size(max = 2000)
    private String description;

    @NotNull
    @JsonProperty("location")
    @Size(max = 2000)
    private String location;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;

    @JsonProperty("call_for_paper_start_date")
    private LocalDate callForPaperStartDate;

    @JsonProperty("call_for_paper_end_date")
    private LocalDate callForPaperEndDate;

    @JsonProperty("in_past")
    private Boolean isConferenceInPast;

    @JsonProperty(value = "new", defaultValue = "0")
    private Integer newTalkCount = 0;

    @JsonProperty(value = "in-progress", defaultValue = "0")
    private Integer inProgressTalkCount = 0;

    @JsonProperty(value = "approved", defaultValue = "0")
    private Integer approvedTalkCount = 0;

    @JsonProperty(value = "rejected", defaultValue = "0")
    private Integer rejectedTalkCount = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getCallForPaperStartDate() {
        return callForPaperStartDate;
    }

    public void setCallForPaperStartDate(LocalDate callForPaperStartDate) {
        this.callForPaperStartDate = callForPaperStartDate;
    }

    public LocalDate getCallForPaperEndDate() {
        return callForPaperEndDate;
    }

    public void setCallForPaperEndDate(LocalDate callForPaperEndDate) {
        this.callForPaperEndDate = callForPaperEndDate;
    }

    public Boolean getConferenceInPast() {
        return isConferenceInPast;
    }

    public void setConferenceInPast(Boolean conferenceInPast) {
        isConferenceInPast = conferenceInPast;
    }

    public Integer getNewTalkCount() {
        return newTalkCount;
    }

    public void setNewTalkCount(Integer newTalkCount) {
        if (newTalkCount == null) {
            newTalkCount = 0;
        }
        this.newTalkCount = newTalkCount;
    }

    public Integer getInProgressTalkCount() {
        return inProgressTalkCount;
    }

    public void setInProgressTalkCount(Integer inProgressTalkCount) {
        if (inProgressTalkCount == null) {
            inProgressTalkCount = 0;
        }
        this.inProgressTalkCount = inProgressTalkCount;
    }

    public Integer getApprovedTalkCount() {
        return approvedTalkCount;
    }

    public void setApprovedTalkCount(Integer approvedTalkCount) {
        if (approvedTalkCount == null) {
            approvedTalkCount = 0;
        }
        this.approvedTalkCount = approvedTalkCount;
    }

    public Integer getRejectedTalkCount() {
        return rejectedTalkCount;
    }

    public void setRejectedTalkCount(Integer rejectedTalkCount) {
        if (rejectedTalkCount == null) {
            rejectedTalkCount = 0;
        }
        this.rejectedTalkCount = rejectedTalkCount;
    }
}
