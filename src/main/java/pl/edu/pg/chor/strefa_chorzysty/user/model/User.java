package pl.edu.pg.chor.strefa_chorzysty.user.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private Email email;

    private String password;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String familyName;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Voice voice;

    @Column(name = "email")
    private String emailValue;

    @PrePersist
    @PreUpdate
    private void syncEmailToValue() {
        if (email != null) {
            this.emailValue = email.toString();
        }
    }

    @PostLoad
    private void syncValueToEmail() {
        if (emailValue != null) {
            this.email = new Email(emailValue);
        }
    }

    private boolean enabled;

    public String getFullName() {
        return firstName + " " + lastName + " (" + familyName + ")";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role::name);
    }

    @Override
    public String getUsername() {
        return getFullName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !password.isEmpty();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
