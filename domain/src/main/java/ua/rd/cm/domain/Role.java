package ua.rd.cm.domain;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Entity
@Table(name = "role")
@SequenceGenerator(name = "seq", allocationSize = 1, sequenceName = "role_seq")
@AttributeOverride(name = "id", column = @Column(name = "role_id"))
public class Role extends AbstractEntity implements GrantedAuthority {
    public static final String ORGANISER = "ROLE_ORGANISER";
    public static final String SPEAKER = "ROLE_SPEAKER";
    public static final String ADMIN = "ROLE_ADMIN";

    @Column(name = "role_name", nullable = false, unique = true)
    private String name;

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
