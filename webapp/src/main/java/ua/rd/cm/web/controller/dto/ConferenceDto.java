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
@Data
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

    @JsonProperty(value = "new", defaultValue = "0")
    private Integer newTalkCount;

    @JsonProperty(value = "in-progress", defaultValue = "0")
    private Integer inProgressTalkCount;

    @JsonProperty(value = "approved", defaultValue = "0")
    private Integer approvedTalkCount;

    @JsonProperty(value = "rejected", defaultValue = "0")
    private Integer rejectedTalkCount;
}
