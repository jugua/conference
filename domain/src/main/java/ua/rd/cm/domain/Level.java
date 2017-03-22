package ua.rd.cm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "level")
@SequenceGenerator(name = "seq", allocationSize = 1, sequenceName = "level_seq")
@AttributeOverride(name = "id", column = @Column(name = "level_id"))
public class Level extends AbstractEntity {

    @Column(name = "level_name", nullable = false, unique = true)
    private String name;

    public Level(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
