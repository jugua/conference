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
@Table(name = "type")
@SequenceGenerator(name = "seq", allocationSize = 1, sequenceName = "type_seq")
@AttributeOverride(name = "id", column = @Column(name = "type_id"))
public class Type extends AbstractEntity {

    @Column(name = "type_name", nullable = false, unique = true)
    private String name;

    public Type(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
