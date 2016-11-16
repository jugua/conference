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
@Table(name = "user", indexes = {
        @Index(name = "email_index",  columnList="email", unique = true)
})
@SequenceGenerator(name = "seqUserGen", allocationSize = 1)
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

    @ManyToMany
    @JoinTable(name = "user_role")
    private Set<Role> userRoles = new HashSet<>();

    public boolean addRole(Role role) {
        return userRoles.add(role);
    }

	public User(String firstName, String lastName, String email, String password, String photo, UserInfo userInfo,
			Set<Role> userRoles) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.photo = photo;
		this.userInfo = userInfo;
		this.userRoles = userRoles;
	}

}