package ua.rd.cm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateTypeDto {
    @JsonProperty("name")
    private String name;
}
