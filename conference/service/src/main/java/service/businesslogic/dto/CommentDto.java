package service.businesslogic.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;
    @NonNull
    private String message;
    @NonNull
    private Long userId;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private Long talkId;
    @NonNull
    private LocalDateTime time;

    @Builder
    public CommentDto(Long id, Long talkId,
                      Long userId, String firstName, String lastName,
                      String message, LocalDateTime time) {
        this.id = id;
        this.message = message;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.talkId = talkId;
        this.time = time;
    }
}
