package com.recruiter.util;

import com.google.gson.Gson;
import com.recruiter.model.User;
import com.recruiter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class HomeHandler {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String home() {
        return ("<h1>Welcome</h1>");
    }

    @GetMapping("/user")
    public String user() {
        return ("<h1>Welcome User</h1>");
    }

    @GetMapping("/admin")
    public String admin() {
        return ("<h1>Welcome Admin</h1>");
    }

    @GetMapping("/ttt")
    public ResponseEntity<String> getAccounts() {
        Optional<User> users = Optional.ofNullable(userRepository.findByEmail("ttt@ttt.com"));
        userRepository.deleteByUserName("ttt");
        Gson gson = new Gson();
        String json = gson.toJson(users);
//        String outStr = "";
//        for(int i = 0; i<users.size(); i++){
//            User curUser = users.get(i);
//            outStr = outStr + curUser.getUserId() + " " + curUser.getFirstName()+ " " + curUser.getLastName() + " " + curUser.getEmail() +
//                    " " + curUser.getAccountType() + " " + curUser.getPassword() + "\n";
//        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping("/t2")
    public ResponseEntity<String> getAll() {
        List<User> users = userRepository.findAll();
        Gson gson = new Gson();
        String json = gson.toJson(users);
//        String outStr = "";
//        for(int i = 0; i<users.size(); i++){
//            User curUser = users.get(i);
//            outStr = outStr + curUser.getUserId() + " " + curUser.getFirstName()+ " " + curUser.getLastName() + " " + curUser.getEmail() +
//                    " " + curUser.getAccountType() + " " + curUser.getPassword() + "\n";
//        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping("/t3")
    public String t3() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return "Hello:" + name;
    }
}
