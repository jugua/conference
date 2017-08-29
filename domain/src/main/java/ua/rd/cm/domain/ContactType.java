package ua.rd.cm.domain;

import javax.persistence.*;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Entity
@Table(name = "contact_type")
@SequenceGenerator(name = "seq", allocationSize = 1, sequenceName = "contact_type_seq")
@AttributeOverride(name = "id", column = @Column(name = "contact_type_id"))
public class ContactType extends AbstractEntity {

    @Column(name = "contact_type_name", nullable = false, unique = true)
    private String name;

    public ContactType(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}