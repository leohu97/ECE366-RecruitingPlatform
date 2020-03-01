package com.recruiter;

import com.recruiter.model.User;
import com.recruiter.store.JobStore;
import com.recruiter.store.UserStore;
import com.recruiter.util.JobHandler;
import com.recruiter.util.UserHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;
@RestController
@RequestMapping("/users")
public class Handler {

    private User user;
    private UserHandler userHandler;
    private JobHandler jobHandler;
    private UserStore userStore;
    private JobStore jobStore;

    @PostMapping("login")
    public ResponseEntity<String> logIn(
            @RequestParam(value = "password", defaultValue="") String password,
            @RequestParam(value = "email", defaultValue="") String email) {

        this.userStore = new UserStore();
        User user = userStore.getUser(email, password);
        if(user == null) {

        } else {
            this.user = user;
            String outStr = "User " + user.getEmail() + " has successfully logged in!";
            return new ResponseEntity<>(outStr, HttpStatus.OK);

        }

        return new ResponseEntity<>("Could not log in!", HttpStatus.FORBIDDEN);


    }



}
