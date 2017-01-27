package ua.rd.cm.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Artem_Pryzhkov
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@ToString(exclude = "id")
@Entity
@Table(name = "role")
@SequenceGenerator(name = "seqRoleGen", allocationSize = 1,
        sequenceName = "role_seq")
public class Role implements GrantedAuthority {

    public static final String ORGANISER = "ROLE_ORGANISER";
    public static final String SPEAKER = "ROLE_SPEAKER";
    public static final String ADMIN = "ROLE_ADMIN";

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqRoleGen")
    private Long id;

    @Column(name = "role_name", nullable = false, unique = true)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
