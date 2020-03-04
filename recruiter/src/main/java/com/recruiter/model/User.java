package com.recruiter.model;

import org.springframework.web.bind.annotation.RequestMapping;

public class User {
    private String firstName;
    private String lastName;
    private String accountType; // applicant or company
    private String email;
    private String password;
    private Long userId;

    public User(Long userId, String firstName, String lastName, String accountType, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountType = accountType;
        this.email = email;
        this.userId = userId;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
