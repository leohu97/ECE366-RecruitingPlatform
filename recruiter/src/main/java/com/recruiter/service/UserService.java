package com.recruiter.service;

import com.recruiter.model.User;
import com.recruiter.store.UserStore;
import com.recruiter.util.UserHandler;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class UserService {

    private UserStore userStore;

    public UserService(UserStore userStore) {
        this.userStore = userStore;
    }

    // -1 : one of the fields is blank
    // 1 : user with the given email already exists
    // 0 : account was created
    // 2 : invalid email
    public int createAccount(UserHandler.CreateUserRequest createUserRequest) {
        Optional<User> user = Optional.ofNullable(userStore.findUserByEmail(createUserRequest.getEmail()));
        if (createUserRequest.getEmail().equals("")
            || createUserRequest.getFirstName().equals("")
            || createUserRequest.getLastName().equals("")
            || createUserRequest.getAccountType().equals("")
            || createUserRequest.getPassword().equals("")) {
            return -1;
        } else if(!isValidEmail(createUserRequest.getEmail())) {
            return 2;
        } else if(user.isPresent()) {
            return 1;
        } else {
            userStore.addUser(createUserRequest);
            return 0;
        }
    }

    // -1 : user does not exist
    // 0 : user is a company
    // 1 : user is an applicant
    public int isValidUser(Long userId) {
        Optional<User> user = Optional.ofNullable(userStore.findUserById(userId));
        if(user.isPresent()) {
            if(user.get().getAccountType().equals("Company")) {
                return 0;
            } else if (user.get().getAccountType().equals("Applicant")){
                return 1;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    // 0 : successful login
    // -1 : no account with the given email exists
    // 1 : email exists put password is wrong
    // 2 : email is not a valid email
    public int logIn(String email, String password) {
        Optional<User> user = Optional.ofNullable(userStore.findUserByEmail(email));
        if (!isValidEmail(email)) {
            return 2;
        } else if (user.isPresent()) {
            if (user.get().getPassword().equals(password)) {
                return 0;
            } else {
                return 1;
            }
        } return -1;
    }

    public boolean isValidEmail(String email) {
         String regex = "^(.+)@(.+)$";
         Pattern pattern = Pattern.compile(regex);
        if (pattern.matcher(email).matches()) {
            return true;
        } else {
            return false;
        }
    }
}
