package ua.rd.cm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateTopicDto {
    @JsonProperty("name")
    private String name;
}
