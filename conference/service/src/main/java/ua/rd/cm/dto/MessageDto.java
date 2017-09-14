package ua.rd.cm.dto;

import java.util.List;

import lombok.Data;

@Data
public class MessageDto {
    private String error;
    private String secondsToExpiry;
    private String result;
    private Long id;
    private List<String> fields;
}
