package ua.rd.cm.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"photo", "userInfo"})
@ToString(exclude = {"password", "photo", "userInfo"})
@Entity
@SequenceGenerator(name = "seq", allocationSize = 1, sequenceName = "user_seq")
public class User extends AbstractEntity {

    @NonNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NonNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NonNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NonNull
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "photo")
    private String photo;

    @NonNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToOne(orphanRemoval = true, cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_info_id", unique = true)
    private UserInfo userInfo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role")
    private Set<Role> roles = new HashSet<>();

    @Builder
    public User(Long id, String firstName, String lastName, String email, String password,
                String photo, UserStatus status, UserInfo userInfo, Set<Role> roles) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.status = status;
        this.userInfo = userInfo;
        this.roles = roles;
    }

    public boolean addRole(Role role) {
        return roles.add(role);
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public List<String> getRoleNames() {
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }

    public enum UserStatus {
        CONFIRMED, UNCONFIRMED
    }
}