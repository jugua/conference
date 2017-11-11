package ua.rd.cm.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

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
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private LocalDate startDate;
    @JsonProperty("end_date")
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private LocalDate endDate;
    @JsonProperty("no_dates")
    private boolean noDates;

    @JsonProperty("cfp_start_date")
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private LocalDate callForPaperStartDate;
    @JsonProperty("cfp_end_date")
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private LocalDate callForPaperEndDate;
    @JsonProperty("cfp_no_dates")
    private boolean noCallForPaperDates;

    @JsonProperty("topics")
    private List<Long> topicIds = new ArrayList<>();
    @JsonProperty("types")
    private List<Long> typeIds = new ArrayList<>();
    @JsonProperty("languages")
    private List<Long> languageIds = new ArrayList<>();
    @JsonProperty("levels")
    private List<Long> levelIds = new ArrayList<>();
    @JsonProperty("organisers")
    private List<Long> organiserIds = new ArrayList<>();
}
