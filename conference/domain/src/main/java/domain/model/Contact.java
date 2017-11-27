package domain.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Table
public class Contact extends AbstractEntity {

    @NonNull
    private String value;

    @NonNull
    private long userInfoId;
    
    @NonNull
    @ManyToOne
    @JoinColumn(name = "contact_type_id", nullable = false)
    private ContactType contactType;

    public Contact(Long id, String value, ContactType contactType) {
        super(id);
        this.value = value;
        this.contactType = contactType;
    }

    public Contact(String value, ContactType contactType) {
        this.value = value;
        this.contactType = contactType;
    }
    
}
