package com.recruiter.service.impl;

import com.recruiter.model.User;
import com.recruiter.repository.UserRepository;
import com.recruiter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        user.setRoles(new HashSet<>(roleRepository.findAll()));
        user.setRoles("ROLE_USER");
        user.setEmail(user.getEmail());
        user.setAccountType(user.getAccountType());

        userRepository.save(user);
    }

    @Override
    public void delete(String username) {
        userRepository.deleteByUserName(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public boolean isCompanyUser(String currentUsername) {
        Optional<User> currentUser = Optional.ofNullable(findByUsername(currentUsername));
        return currentUser.isPresent() && currentUser.get().getAccountType().equals("Company");
    }

    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}

