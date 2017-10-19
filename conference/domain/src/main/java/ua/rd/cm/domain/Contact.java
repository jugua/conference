package ua.rd.cm.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

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
@SequenceGenerator(name = "seq", allocationSize = 1, sequenceName = "contact_seq")
public class Contact extends AbstractEntity {

    @NonNull
    private String value;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "contact_type_id", nullable = false)
    private ContactType contactType;

    public Contact(Long id, String value, ContactType contactType) {
        super(id);
        this.value = value;
        this.contactType = contactType;
    }

}
