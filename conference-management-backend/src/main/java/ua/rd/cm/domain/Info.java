package ua.rd.cm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Artem_Pryzhkov
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class Info {

    private User user;
    private String shortBio;
    private String jobTitle;
    private String pastConference;
    private String company;

    private String linkedIn;
    private String twitter;
    private String facebook;
    private String blog;
    private String additionalInfo;
}
