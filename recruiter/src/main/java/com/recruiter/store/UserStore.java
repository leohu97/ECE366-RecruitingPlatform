package com.recruiter.store;

import com.recruiter.model.Job;
import com.recruiter.model.User;

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



    public List<User> getAllUsers() {
        return new ArrayList<>(usersById.values());
    }

    public boolean isCompany(Long userId) {
        
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
