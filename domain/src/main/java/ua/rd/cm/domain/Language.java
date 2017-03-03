package ua.rd.cm.domain;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "language")
@SequenceGenerator(name = "seq", allocationSize = 1, sequenceName = "language_seq")
@AttributeOverride(name = "id", column = @Column(name = "language_id"))
public class Language extends AbstractEntity {

    @Column(name = "language_name", nullable = false, unique = true)
    private String name;

    public Language(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
