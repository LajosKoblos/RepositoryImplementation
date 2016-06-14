package com.ge.academy.contact_list.repository;

import com.ge.academy.contact_list.entity.ContactGroup;
import com.ge.academy.contact_list.entity.ContactGroupId;
import com.ge.academy.contact_list.exception.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InMemoryContactGroupRepository implements ContactGroupRepository {
    @Override
    public ContactGroup save(ContactGroup contactGroup) throws EntityNotFoundException {
        return null;
    }

    @Override
    public void delete(ContactGroupId contactGroupId) throws EntityNotFoundException {

    }

    @Override
    public ContactGroup findOne(ContactGroupId contactGroupId) throws EntityNotFoundException {
        return null;
    }

    @Override
    public List<ContactGroup> findAll() {
        return null;
    }

    @Override
    public List<ContactGroup> findByOwner(String s) {
        return null;
    }
}
