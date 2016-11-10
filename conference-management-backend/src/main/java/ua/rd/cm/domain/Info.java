package ua.rd.cm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Artem_Pryzhkov
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "info")
public class Info {

    @TableGenerator(
            name = "infoGen",
            table = "info_id_gen",
            pkColumnName = "gen_key",
            valueColumnName = "gen_value",
            pkColumnValue = "info_id",
            allocationSize = 1
    )

    private User user;

    @NotNull
    @Size(max = 2000)
    @Column(name = "short_bio", nullable = false)
    private String shortBio;

    @NotNull
    @Size(max = 256)
    @Column(name = "job_title", nullable = false)
    private String jobTitle;

    @Size(max = 1000)
    @Column(name = "past_conference")
    private String pastConference;

    @NotNull
    @Size(max = 256)
    @Column(name = "company", nullable = false)
    private String company;

    @Column(name = "linkedIn")
    private String linkedIn;
    @Column(name = "twitter")
    private String twitter;
    @Column(name = "facebook")
    private String facebook;
    @Column(name = "blog")
    private String blog;
    @Column(name = "additional_info")
    private String additionalInfo;
}
