package com.ge.academy.contact_list.repository;

import com.ge.academy.contact_list.entity.Contact;
import com.ge.academy.contact_list.exception.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InMemoryContactRepository implements ContactRepository {
    @Override
    public Contact save(Contact contact) throws EntityNotFoundException {
        return null;
    }

    @Override
    public void delete(String s) throws EntityNotFoundException {

    }

    @Override
    public Contact findOne(String s) throws EntityNotFoundException {
        return null;
    }

    @Override
    public List<Contact> findAll() {
        return null;
    }

    @Override
    public Contact findByExample(Contact contact) throws EntityNotFoundException {
        return null;
    }
}
