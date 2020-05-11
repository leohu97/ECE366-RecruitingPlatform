package com.recruiter.util;

import com.google.gson.Gson;
import com.recruiter.model.User;
import com.recruiter.service.SecurityService;
import com.recruiter.service.UserService;
import com.recruiter.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class UserHandler {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getAll(
            @RequestParam("id") Optional<String> id,
            @RequestParam("email") Optional<String> email,
            @RequestParam("username") Optional<String> username,
            @RequestParam("accountType") Optional<String> accountType) {
        List<User> users = userService.findAll();
        for (Optional<String> param : Arrays.asList(id, email, username, accountType)) {
            if (param.isPresent()) {
                users = users.stream()
                        .filter(o -> o.getId().equals(param.get()))
                        .collect(Collectors.toList());
            }

        }


//        if (id.isPresent() || email.isPresent() || username.isPresent() || accountType.isPresent()) {
//            Iterator<User> it1 = users.iterator();
//            while(it1.hasNext()) {
//
//            }
//            users = users.stream().filter(o -> Long.toString(o.getId()).equals(id.get())).collect(Collectors.toList());
//        }

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

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
        return "welcome";
    }
}
