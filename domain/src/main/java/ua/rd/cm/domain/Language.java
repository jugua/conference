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
@Table(name = "language")
@SequenceGenerator(name = "seqLanguageGen", allocationSize = 1,
        sequenceName = "language_seq")
public class Language {

    @Id
    @Column(name = "language_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqLanguageGen")
    private Long id;

    @NotNull
    @Column(name = "language_name", nullable = false, unique = true)
    private String name;
}
