package domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class UserInfo extends AbstractEntity {

    @NonNull
    @Column(nullable = false, length = 2000)
    private String shortBio = "";

    @NonNull
    @Column(nullable = false, length = 256)
    private String jobTitle = "";

    @Column(length = 1000)
    private String pastConference;

    @NonNull
    @Column(nullable = false, length = 256)
    private String company = "";

    @OneToMany(mappedBy = "userInfoId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Contact> contacts = new ArrayList<>();

    @Column(length = 1000)
    private String additionalInfo;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Builder
    public UserInfo(Long id, String shortBio, String jobTitle, String pastConference,
                    String company, List<Contact> contacts, String additionalInfo, User user) {
        super(id);
        this.shortBio = shortBio;
        this.jobTitle = jobTitle;
        this.pastConference = pastConference;
        this.company = company;
        this.contacts = contacts;
        this.additionalInfo = additionalInfo;
        this.user = user;
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public boolean isNotFilled() {
        return shortBio.isEmpty() || jobTitle.isEmpty() || company.isEmpty();
    }

}