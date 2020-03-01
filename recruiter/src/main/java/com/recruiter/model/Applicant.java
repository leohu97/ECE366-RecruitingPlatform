package com.recruiter.model;

public class Applicant extends User {
    public Applicant(Long userId, String firstName, String lastName, String accountType, String email, String password) {
        super(userId, firstName, lastName, accountType, email, password);
    }


}
