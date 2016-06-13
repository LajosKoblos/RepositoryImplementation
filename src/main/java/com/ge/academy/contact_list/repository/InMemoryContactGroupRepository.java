package com.ge.academy.contact_list.repository;

import com.ge.academy.contact_list.entity.ContactGroup;
import com.ge.academy.contact_list.exception.EntityNotFoundException;

import java.util.List;

public class InMemoryContactGroupRepository implements ContactGroupRepository {
    @Override
    public ContactGroup save(ContactGroup contactGroup) throws EntityNotFoundException {
        return null;
    }

    @Override
    public void delete(String s) throws EntityNotFoundException {

    }

    @Override
    public ContactGroup findOne(String s) throws EntityNotFoundException {
        return null;
    }

    @Override
    public List<ContactGroup> findAll() {
        return null;
    }
}
