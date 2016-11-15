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
public class UserInfo {

    @TableGenerator(
            name = "userInfoGen",
            table = "user_info_id_gen",
            pkColumnName = "gen_key",
            valueColumnName = "gen_value",
            pkColumnValue = "user_info_id",
            allocationSize = 1
    )

    @Id
    @Column(name = "user_info_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator =
            "userInfoGen")
    private Long id;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_info_contact",
            joinColumns = @JoinColumn(name = "user_info_id")
    )
    @Column(name = "link")
    @MapKeyJoinColumn(name = "contact_type_id",
            referencedColumnName = "contact_type_id")
    private Map<ContactType, String> contacts = new HashMap<>();

    @Size(max = 1000)
    @Column(name = "additional_info")
    private String additionalInfo;
}