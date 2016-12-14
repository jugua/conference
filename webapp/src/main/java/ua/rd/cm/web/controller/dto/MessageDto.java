package ua.rd.cm.web.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class MessageDto {
    private String error;
    private String status;
    private String answer;
    private List<String> fields;
}
