package ua.rd.cm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Anastasiia_Milinchuk on 2/9/2017.
 */
@Data
@NoArgsConstructor
public class ConferenceDtoBasic {
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
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    @JsonProperty("call_for_paper_start_date")
    private String callForPaperStartDate;

    @JsonProperty("call_for_paper_end_date")
    private String callForPaperEndDate;

    @JsonProperty("cfp_active")
    private Boolean callForPaperActive;
}
