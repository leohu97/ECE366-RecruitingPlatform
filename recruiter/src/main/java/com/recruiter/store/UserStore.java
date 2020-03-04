package com.recruiter.store;

import com.recruiter.model.Job;
import com.recruiter.model.User;
import com.recruiter.util.UserHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class UserStore {
    private final ConcurrentMap<Long, User> usersById;
    private final AtomicLong nextId = new AtomicLong(4);

    public UserStore(){
        this.usersById =
                List.of(
                        new User(1L, "Omar", "Thenmalai", "Applicant", "omar.thenmalai@gmail.com", "password"),
                        new User(2L, "Leo", "Hu", "Applicant", "test@gmail.com", "password"),
                        new User(3L, "Ethan", "Lusterman", "Company", "ethan@spotify.com", "password"))
                        .stream()
                        .collect(Collectors.toConcurrentMap(job -> job.getUserId(), user -> user));
    }



    public List<User> getUsers() {
        return new ArrayList<>(usersById.values());
    }

    public User findUserByEmail(String email) {
        Iterator<Long> it1 = usersById.keySet().iterator();
        while(it1.hasNext()) {
            Long key = it1.next();
            User user = usersById.get(key);
            if (user.getEmail().equals(email))
                return user;
        }
        return null;
    }

    public User findUserById(Long userId) {
        Iterator<Long> it1 = usersById.keySet().iterator();
        while(it1.hasNext()) {
            Long key = it1.next();
            if (userId.equals(key)) {
                User user = usersById.get(key);
                return user;
            }
        }
        return null;
    }

    public void addUser(UserHandler.CreateUserRequest createUserRequest) {
        long userId = nextId.getAndIncrement();
        User user = new User(userId, createUserRequest.getFirstName(), createUserRequest.getLastName(), createUserRequest.getAccountType(), createUserRequest.getEmail(), createUserRequest.getPassword());
        usersById.put(userId, user);
    }

    public User getUser(String email, String password) {
        Iterator<Long> it1 = usersById.keySet().iterator();
        while(it1.hasNext()) {
            Long key = it1.next();
            User user = usersById.get(it1);
            if (user.getEmail().equals(email) && user.getPassword().equals(password))
                return user;
        }
        return null;
    }


}
