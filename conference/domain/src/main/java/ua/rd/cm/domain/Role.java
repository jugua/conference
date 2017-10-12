package ua.rd.cm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@SequenceGenerator(name = "seq", allocationSize = 1, sequenceName = "role_seq")
public class Role extends AbstractEntity implements GrantedAuthority {

    public static final String ORGANISER = "ROLE_ORGANISER";
    public static final String SPEAKER = "ROLE_SPEAKER";
    public static final String ADMIN = "ROLE_ADMIN";

    @NonNull
    @Column(nullable = false, unique = true)
    private String name;

    public Role(Long id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
