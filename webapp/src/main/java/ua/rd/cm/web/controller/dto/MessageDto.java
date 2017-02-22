package ua.rd.cm.web.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class MessageDto {
    private String error;
    private String secondsToExpiry;
    private String result;
    private Long id;
    private List<String> fields;
}
