package ua.rd.cm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author Artem_Pryzhkov
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class Talk {

    private Long id;
    private User user;
    private Status status;
    private Topic topic;
    private LocalDate date;
    private String title;
    private String description;
    private Language language;
    private Level level;
    private String additionalInfo;
    private Type type;
}
