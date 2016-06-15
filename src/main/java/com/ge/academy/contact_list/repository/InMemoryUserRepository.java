package com.ge.academy.contact_list.repository;

import com.ge.academy.contact_list.entity.User;
import com.ge.academy.contact_list.entity.UserRole;
import org.springframework.stereotype.Repository;
import com.ge.academy.contact_list.exception.EntityNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Lock LOCK = new ReentrantLock();
    private Map<String, User> users = new HashMap<>();
    {
        seed();
    }

    public InMemoryUserRepository() {
        this(new HashMap<>());
    }

    public InMemoryUserRepository(Map<String, User> users) {
        this.users = users;
    }

    private void seed() {
        users.put("Admin", new User("Admin", "Alma1234", UserRole.ADMIN));
    }

    @Override
    public User save(User user) throws EntityNotFoundException {
        User thisUser = new User(user);
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
    public void delete(String s) throws EntityNotFoundException {

        try {
            LOCK.lock();
            System.out.println("Removing user " + s);

            User removedUser = users.remove(s);
            if (removedUser == null) throw new EntityNotFoundException(User.class, s);

        }

        finally {
            LOCK.unlock();
        }

    }

    @Override
    public User findOne(String s) throws EntityNotFoundException {
        try {
            LOCK.lock();

            System.out.println("Finding user " + s);

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
            System.out.println("Finding all users...");

            return users.values().stream().map(User::new).collect(Collectors.toList());
        }

        finally {
            LOCK.unlock();
        }
    }
}
