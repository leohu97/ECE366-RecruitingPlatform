package com.recruiter.util;

import com.google.gson.Gson;
import com.recruiter.model.Job;
import com.recruiter.model.User;
import com.recruiter.repository.UserRepository;
import com.recruiter.service.SecurityService;
import com.recruiter.service.UserService;
import com.recruiter.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getAll(
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "accountType", required = false) String accountType) {
        if (null == id && null == email && null == username && null == accountType) {
            return new ResponseEntity("At least one parameter is required", HttpStatus.BAD_REQUEST);
        } else {
            User user = new User();
            user.setId(id);

            Example<User> ex = Example.of(user);

            List<User> result = userRepository.findAll(ex);
            return new ResponseEntity(result, HttpStatus.OK);
        }
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
