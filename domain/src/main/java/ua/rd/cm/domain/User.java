package ua.rd.cm.domain;

import lombok.*;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mariia Lapovska
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Entity
@Table(name = "user")
@SequenceGenerator(name = "seqUserGen", allocationSize = 1,
        sequenceName = "user_seq")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqUserGen")
    private Long id;

    @NotNull
    @Size(min = 1, max = 56)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 56)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")
    @Column(name = "email", nullable = false, unique =  true)
    private String email;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "photo", nullable = false)
    private String photo;

    @OneToOne(orphanRemoval = true, cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_info_id", unique = true)
    private UserInfo userInfo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role")
    private Set<Role> userRoles = new HashSet<>();

    public boolean addRole(Role role) {
        return userRoles.add(role);
    }

}