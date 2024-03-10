package com.elevenqtwo.Effective_Mobile_TestApp.model;

import com.elevenqtwo.Effective_Mobile_TestApp.exception.UserNotFoundException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"login"})
})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    private String firstName;

    @Setter
    private String lastName;

    @Setter
    private String middleName;

    @Setter
    @Column(nullable = false, unique = true)
    private String login;

    @Setter
    @Column(nullable = false)
    private String password; //TODO hashing

    @Setter
    private Date dateOfBirth;

    @Setter
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "emails",
            joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints =  { @UniqueConstraint(columnNames = {"email"} ) })
    @Column(name = "email", nullable = false)
    private List<String> emails;

    @Setter
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "phone_numbers",
            joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = { @UniqueConstraint(columnNames = { "phone_number"} ) })
    @Column(name = "phone_number", nullable = false)
    private List<String> phoneNumbers;

    @Setter
    @OneToOne
    @JoinColumn(name = "bank_account_id", nullable = false)
    private BankAccount bankAccount;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("NO_ROLE"));
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}