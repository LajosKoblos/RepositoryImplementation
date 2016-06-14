package com.ge.academy.contact_list.repository;

import com.ge.academy.contact_list.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InMemoryUserRepository implements UserRepository {
    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void delete(String s) {

    }

    @Override
    public User findOne(String s) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
