package service.businesslogic.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageDto {
    private String error;
    private String secondsToExpiry;
    private String result;
    private Long id;
    private List<String> fields;

    public MessageDto(Long id) {
        this.id = id;
    }

    public MessageDto(Long id, String error) {
        this(error);
        this.id = id;
    }

    public MessageDto(String error) {
        this.error = error;
    }
}
