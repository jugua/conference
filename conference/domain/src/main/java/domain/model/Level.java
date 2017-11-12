package domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Level extends AbstractEntity {

    @NonNull
    @Column(name = "level_name", nullable = false, unique = true)
    private String name;

    public Level(Long id, String name) {
        super(id);
        this.name = name;
    }
}
