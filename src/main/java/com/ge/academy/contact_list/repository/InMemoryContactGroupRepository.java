package com.ge.academy.contact_list.repository;

import com.ge.academy.contact_list.entity.ContactGroup;
import com.ge.academy.contact_list.entity.ContactGroupId;
import com.ge.academy.contact_list.exception.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryContactGroupRepository implements ContactGroupRepository {

    private ConcurrentMap<ContactGroupId, ContactGroup> contactGroups = new ConcurrentHashMap<>();


    @Override
    public ContactGroup save(ContactGroup contactGroup) throws EntityNotFoundException {
        contactGroups.put(contactGroup.getId(), contactGroup);
        return contactGroup;
    }

    @Override
    public void delete(ContactGroupId contactGroupId) throws EntityNotFoundException {
        if (contactGroups.get(contactGroupId) == null) {
            throw new EntityNotFoundException(contactGroupId.getClass(), contactGroupId);
        } else {
            contactGroups.remove(contactGroupId);
        }

    }

    @Override
    public ContactGroup findOne(ContactGroupId contactGroupId) throws EntityNotFoundException {
        if (contactGroups.get(contactGroupId) == null) {
            throw new EntityNotFoundException(contactGroupId.getClass(), contactGroupId);
        } else {
            final ContactGroup managed = contactGroups.get(contactGroupId);
            return managed;
        }
    }

    @Override
    public List<ContactGroup> findAll() {
        return contactGroups.values().stream().map(mapped -> new ContactGroup(mapped)).collect(Collectors.toList());
    }

    @Override
    public List<ContactGroup> findByOwner(String s) {

        List<ContactGroup> ownersContactGroups = new ArrayList<>();
        for (ContactGroup contactGroup : contactGroups.values()) {
            if (contactGroup.getId().getUserName().equals(s)) {
                ownersContactGroups.add(new ContactGroup(contactGroup));
            }
        }

        return ownersContactGroups;
    }
}
