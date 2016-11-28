package ua.rd.cm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Artem_Pryzhkov
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Entity
@Table(name = "status")
@SequenceGenerator(name = "seqStatusGen", allocationSize = 1,
        sequenceName = "status_seq")
public class Status {

    @Id
    @Column(name = "status_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqStatusGen")
    private Long id;

    @NotNull
    @Column(name = "status_name", nullable = false, unique = true)
    private String name;
}
