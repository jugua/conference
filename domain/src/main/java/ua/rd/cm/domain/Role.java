package ua.rd.cm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@Entity
@Table(name = "role")
@SequenceGenerator(name = "seqRoleGen", allocationSize = 1,
        sequenceName = "role_seq")
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqRoleGen")
    private Long id;

    @NotNull
    @Column(name = "role_name", nullable = false, unique = true)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
