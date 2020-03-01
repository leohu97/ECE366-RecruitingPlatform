package com.recruiter.util;

import com.recruiter.model.Job;
import com.recruiter.model.User;
import com.recruiter.service.UserService;
import com.recruiter.store.UserStore;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserHandler {

    private UserService userService;
    private UserStore userStore;

    @Autowired
    public UserHandler() {
        this.userStore = new UserStore();
        this.userService = new UserService(userStore);
    }

    @PostMapping("login")
    public ResponseEntity<String> logIn(
            @RequestParam(value = "password", defaultValue="") String password,
            @RequestParam(value = "email", defaultValue="") String email) {
        int status = userService.logIn(email, password);
        if(status == 2) {
            return new ResponseEntity<>("Not a valid email!", HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (status == 1) {
            return new ResponseEntity<>("Incorrect Password!", HttpStatus.UNAUTHORIZED);
        } else if(status == -1) {
            return new ResponseEntity<>("No account with that email exists!", HttpStatus.NOT_FOUND);
        } else if(status == 0){
            return new ResponseEntity<>("Successfully logged in!", HttpStatus.OK);
        }
        return new ResponseEntity<>("ERROR!", HttpStatus.NOT_FOUND);
    }

    @PostMapping("createAccount")
    public ResponseEntity<String> createAccount(
            @RequestParam(value = "firstName", defaultValue = "") String firstName,
            @RequestParam(value = "lastName", defaultValue = "") String lastName,
            @RequestParam(value = "email", defaultValue = "") String email,
            @RequestParam(value = "accountType", defaultValue = "") String accountType,
            @RequestParam(value = "password", defaultValue = "") String password) {

        CreateUserRequest createUserRequest = new CreateUserRequest(firstName, lastName, accountType, email, password);
        int status = userService.createAccount(createUserRequest);
        if(status == 2) {
            return new ResponseEntity<>("Not a valid email!", HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (status == 1) {
            return new ResponseEntity<>("An account with the email, " + email +", already exists!", HttpStatus.CONFLICT);
        } else if(status == -1) {
            return new ResponseEntity<>("At least one of the required fields is empty!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Created account!", HttpStatus.OK);
        }
    }

    // for testing
    @GetMapping("getAccounts")
    public ResponseEntity<String> getAccounts() {
        List<User> users = userStore.getUsers();
        String outStr = "";
        for(int i = 0; i<users.size(); i++){
            User curUser = users.get(i);
            outStr = outStr + curUser.getUserId() + " " + curUser.getFirstName()+ " " + curUser.getLastName() + " " + curUser.getEmail() +
                    " " + curUser.getAccountType() + " " + curUser.getPassword() + "\n";
        }
        return new ResponseEntity<>(outStr, HttpStatus.OK);
    }


    public static class CreateUserRequest {
        private String firstName;
        private String lastName;
        private String accountType; // applicant or company
        private String email;
        private String password;

        public CreateUserRequest(String firstName, String lastName, String accountType, String email, String password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.accountType = accountType;
            this.email = email;
            this.password = password;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getAccountType() {
            return accountType;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }
}
