package com.ge.academy.contact_list.repository;

import com.ge.academy.contact_list.entity.User;
import com.ge.academy.contact_list.entity.UserRole;
import com.ge.academy.contact_list.exception.ValidationException;
import org.springframework.stereotype.Repository;
import com.ge.academy.contact_list.exception.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Lock LOCK = new ReentrantLock();
    private Map<String, User> users;

    List<String> exceptionList = new ArrayList<String>();

    public InMemoryUserRepository() {
        this(new HashMap<>());
        seed();
    }

    public InMemoryUserRepository(Map<String, User> users) {
        this.users = users;
    }

    private void seed() {
        users.put("Admin", new User("Admin", "Alma1234", UserRole.ADMIN));
    }

    @Override
    public User save(User user) throws EntityNotFoundException, ValidationException {
        User thisUser = new User(user);

        // Error handling
        Map<String, ArrayList<String>> errors = new HashMap<>();
        errors.put("userName", new ArrayList<>());
        errors.put("password", new ArrayList<>());
		errors.put("role", new ArrayList<>());

        if (thisUser.getUserName() == null || thisUser.getUserName().isEmpty()) errors.get("userName").add("This field is required.");
        if (thisUser.getPassword() == null ||thisUser.getPassword().isEmpty()) errors.get("password").add("This field is required.");
        if (thisUser.getRole() == null) errors.get("role").add("Users must have a role");

        for (Map.Entry<String, ArrayList<String>> entry : errors.entrySet()) {
            if (entry.getValue().size() != 0) throw new ValidationException(errors);
        }
        // End of error handling

        try {
            LOCK.lock();

//      Can be a User update
            if (users.containsKey(thisUser.getUserName()))
                System.out.println("User " + thisUser.getUserName() + " updated!");
//      Or a new user
            else
                System.out.println("User " + thisUser.getUserName() + " saved!");

            users.put(thisUser.getUserName(), thisUser);
            return thisUser;
        }

        finally {
            LOCK.unlock();
        }
    }

    @Override
    public void delete(String userName) throws EntityNotFoundException {

        try {
            LOCK.lock();

            if (!users.containsKey(userName)) {
                throw new EntityNotFoundException(User.class, userName);
            }
            User removedUser = users.remove(userName);
            if (removedUser == null) throw new EntityNotFoundException(User.class, userName);

        }

        finally {
            LOCK.unlock();
        }

    }

    @Override
    public User findOne(String s) throws EntityNotFoundException {
        try {
            LOCK.lock();

            User thisUser = users.get(s);

            if (thisUser == null) throw new EntityNotFoundException(User.class, s);

            else return thisUser;
        }

        finally {
            LOCK.unlock();
        }
    }

    @Override
    public List<User> findAll() throws EntityNotFoundException {
        try {
            LOCK.lock();

            return users.values().stream().map(User::new).collect(Collectors.toList());
        }

        finally {
            LOCK.unlock();
        }
    }
}
