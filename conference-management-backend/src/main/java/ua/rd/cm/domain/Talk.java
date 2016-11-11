package ua.rd.cm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * @author Artem_Pryzhkov
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Entity
@Table(name = "talk")
public class Talk {

    @TableGenerator(
            name = "talkGen",
            table = "talk_id_gen",
            pkColumnName = "gen_key",
            valueColumnName = "gen_value",
            pkColumnValue = "talk_id",
            allocationSize = 1
    )

    @Id
    @Column(name = "talk_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "talkGen")
    private Long id;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id")
    private Status status;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    //TODO adapter to date
    private LocalDate date;

    @NotNull
    @Size(max = 250)
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Size(max = 3000)
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "language_id")
    private Language language;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "level_id")
    private Level level;

    @NotNull
    @Size(max = 1500)
    @Column(name = "additional_info", nullable = false)
    private String additionalInfo;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id")
    private Type type;
}
