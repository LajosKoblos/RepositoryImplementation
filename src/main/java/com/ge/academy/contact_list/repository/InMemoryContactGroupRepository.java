package com.ge.academy.contact_list.repository;

import com.ge.academy.contact_list.entity.ContactGroup;
import com.ge.academy.contact_list.entity.ContactGroupId;
import com.ge.academy.contact_list.exception.EntityNotFoundException;

import java.util.List;

public class InMemoryContactGroupRepository implements ContactGroupRepository {

    @Override
    public ContactGroup save(ContactGroup contactGroup) throws EntityNotFoundException {
        return null;
    }

    @Override
    public void delete(ContactGroupId contactId) throws EntityNotFoundException {

    }

    @Override
    public ContactGroup findOne(ContactGroupId contactId) throws EntityNotFoundException {
        return null;
    }

    @Override
    public List<ContactGroup> findAll() {
        return null;
    }

    @Override
    public List<ContactGroup> findByOwner(String userName) {
        return null;
    }
}
