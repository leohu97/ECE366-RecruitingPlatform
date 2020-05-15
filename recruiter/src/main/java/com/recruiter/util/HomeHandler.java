package com.recruiter.util;

import com.google.gson.Gson;
import com.recruiter.model.User;
import com.recruiter.repository.UserRepository;
import com.recruiter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class HomeHandler {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;


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
        Optional<User> users = Optional.ofNullable(userService.findByEmail("ttt@ttt.com"));
        userService.delete("ttt");
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

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserNameSimple(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            return principal.getName();
        }
        else return null;
    }

    @RequestMapping(value = "/t4", method = RequestMethod.DELETE)
    public void deleteUser(@RequestParam(name = "username") String username) {
//        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));
        userRepository.deleteByUserName(username);

    }

}
