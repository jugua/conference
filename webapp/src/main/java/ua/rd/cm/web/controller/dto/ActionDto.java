package ua.rd.cm.web.controller.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ActionDto {

    @Size(max = 1000, message = "comment_too_long")
    private String comment;

    private String status;
}
