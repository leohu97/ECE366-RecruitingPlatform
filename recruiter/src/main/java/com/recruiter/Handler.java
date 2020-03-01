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




}
