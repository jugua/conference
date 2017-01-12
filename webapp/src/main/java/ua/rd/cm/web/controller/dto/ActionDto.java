package ua.rd.cm.web.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ActionDto {
    @NotNull
    @Size(max=1000)
    private String comment;
    private String status;
}
