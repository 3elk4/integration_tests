package edu.iis.mto.blog.domain.model;

import javax.persistence.*;

@Entity
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

}
