package ua.rd.cm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Artem_Pryzhkov
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_info")
@SequenceGenerator(name = "seqUserInfoGen", allocationSize = 1,
        sequenceName = "user_info_seq")
public class UserInfo {

    @Id
    @Column(name = "user_info_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqUserInfoGen")
    private Long id;

    @Column(name = "short_bio", nullable = false)
    private String shortBio = "";

    @Column(name = "job_title", nullable = false)
    private String jobTitle = "";

    @Column(name = "past_conference")
    private String pastConference;

    @Column(name = "company", nullable = false)
    private String company = "";

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_info_contact",
            joinColumns = @JoinColumn(name = "user_info_id")
    )
    @Column(name = "link")
    @MapKeyJoinColumn(name = "contact_type_id",
            referencedColumnName = "contact_type_id")
    private Map<ContactType, String> contacts = new HashMap<>();

    @Column(name = "additional_info")
    private String additionalInfo;

}