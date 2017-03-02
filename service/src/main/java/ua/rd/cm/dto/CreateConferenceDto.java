package ua.rd.cm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
public class CreateConferenceDto {

    @NotNull
    @Size(max = 1000)
    @JsonProperty("title")
    private String title;

    @NotNull
    @Size(max = 3000)
    @JsonProperty("description")
    private String description;

    @NotNull
    @Size(max = 256)
    @JsonProperty("location")
    private String location;

    @JsonProperty("start_date")
    private LocalDate startDate;
    @JsonProperty("end_date")
    private LocalDate endDate;
    @JsonProperty("no_dates")
    private boolean noDates;

    @JsonProperty("cfp_start_date")
    private LocalDate callForPaperStartDate;
    @JsonProperty("cfp_end_date")
    private LocalDate callForPaperEndDate;
    @JsonProperty("cfp_no_dates")
    private boolean noCallForPaperDates;

    @JsonProperty("topics")
    private List<Long> topicIds;
    @JsonProperty("types")
    private List<Long> typeIds;
    @JsonProperty("languages")
    private List<Long> languageIds;
    @JsonProperty("levels")
    private List<Long> levelIds;
    @JsonProperty("organisers")
    private List<Long> organiserIds;
}
