package ua.rd.cm.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@ToString(exclude = "id")
@Entity
@SequenceGenerator(name = "seqConfGen", allocationSize = 1,
        sequenceName = "conf_seq")
@Table(name = "conference")
public class Conference {

    @Id
    @Column(name = "conference_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqConfGen")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "call_for_paper_start_date")
    private LocalDate callForPaperStartDate;

    @Column(name = "call_for_paper_end_date")
    private LocalDate callForPaperEndDate;

    @Column(name = "path_to_logo")
    private String pathToLogo;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Talk> talks;
}
