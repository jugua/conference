package ua.rd.cm.domain;

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
public class Topic extends AbstractEntity {

    @NonNull
    @Column(nullable = false, unique = true)
    private String name;

    public Topic(Long id, String name) {
        super(id);
        this.name = name;
    }
}
