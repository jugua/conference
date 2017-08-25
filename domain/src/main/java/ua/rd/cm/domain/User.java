package ua.rd.cm.domain;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = {"password", "photo", "userInfo"})
@Entity
@Table(name = "user")
@SequenceGenerator(name = "seq", allocationSize = 1, sequenceName = "user_seq")
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
public class User extends AbstractEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "photo")
    private String photo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToOne(orphanRemoval = true, cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_info_id", unique = true)
    private UserInfo userInfo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role")
    private Set<Role> userRoles = new HashSet<>();

    public boolean addRole(Role role) {
        return userRoles.add(role);
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public List<String> getRoleNames() {
        List<String> roleNames = new ArrayList<>();
        userRoles.forEach(role -> roleNames.add(role.getName()));
        return roleNames;
    }

    public enum UserStatus {
        CONFIRMED, UNCONFIRMED
    }
}