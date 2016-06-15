package com.ge.academy.contact_list.repository;

import com.ge.academy.contact_list.entity.ContactGroup;
import com.ge.academy.contact_list.entity.ContactGroupId;
import com.ge.academy.contact_list.exception.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryContactGroupRepository implements ContactGroupRepository {

    private Map<ContactGroupId, ContactGroup> contactGroups;

    public InMemoryContactGroupRepository() {
        contactGroups = new ConcurrentHashMap<>();
    }

    public InMemoryContactGroupRepository(Map<ContactGroupId, ContactGroup> contactGroups) {
        this.contactGroups = contactGroups;
    }

    @Override
    public ContactGroup save(ContactGroup contactGroup) throws EntityNotFoundException, IllegalArgumentException {

        if (contactGroup.getId() == null){
            throw new IllegalArgumentException("contactgroupId is null");
        }

        if (contactGroup.getId().getContactGroupName() == null){
            throw new IllegalArgumentException("id.contactGroupName is missing");
        }

        if (contactGroup.getId().getContactGroupName().equals("")){
            throw new IllegalArgumentException("id.contactGroupName is emptyString");
        }

        if (contactGroup.getId().getUserName() == null){
            throw new IllegalArgumentException("id.username is null");
        }

        if (contactGroup.getId().getUserName().isEmpty()){
            throw new IllegalArgumentException("id.username is empty");
        }


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
