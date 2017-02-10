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
public class ConferenceDto extends ConferenceDtoBasic{

    @JsonProperty("call_for_paper_start_date")
    private LocalDate callForPaperStartDate;

    @JsonProperty("call_for_paper_end_date")
    private LocalDate callForPaperEndDate;

    @JsonProperty(value = "new", defaultValue = "0")
    private Integer newTalkCount = 0;

    @JsonProperty(value = "in-progress", defaultValue = "0")
    private Integer inProgressTalkCount = 0;

    @JsonProperty(value = "approved", defaultValue = "0")
    private Integer approvedTalkCount = 0;

    @JsonProperty(value = "rejected", defaultValue = "0")
    private Integer rejectedTalkCount = 0;

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
