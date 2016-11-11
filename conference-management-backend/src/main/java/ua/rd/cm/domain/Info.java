package ua.rd.cm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @Id
    private Long id;

    @JoinColumn(name = "ID")
    @OneToOne
    @MapsId
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

    @Size(max = 1000)
    @Column(name = "linkedIn")
    private String linkedIn;

    @Size(max = 1000)
    @Column(name = "twitter")
    private String twitter;

    @Size(max = 1000)
    @Column(name = "facebook")
    private String facebook;

    @Size(max = 1000)
    @Column(name = "blog")
    private String blog;

    @Size(max = 1000)
    @Column(name = "additional_info")
    private String additionalInfo;
}