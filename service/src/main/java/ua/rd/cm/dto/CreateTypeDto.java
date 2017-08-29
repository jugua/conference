package ua.rd.cm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTypeDto {
    @JsonProperty("name")
    private String name;
}
