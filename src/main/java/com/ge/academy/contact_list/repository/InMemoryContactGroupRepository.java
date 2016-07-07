package com.ge.academy.contact_list.repository;

import com.ge.academy.contact_list.entity.ContactGroup;
import com.ge.academy.contact_list.entity.ContactGroupId;
import com.ge.academy.contact_list.exception.EntityNotFoundException;
import com.ge.academy.contact_list.exception.ValidationException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryContactGroupRepository implements ContactGroupRepository {

    private Map<ContactGroupId, ContactGroup> contactGroups;

    public InMemoryContactGroupRepository() {
        contactGroups = new ConcurrentHashMap<>();
    }
    List<String> exceptionList = new ArrayList<String>();

    public InMemoryContactGroupRepository(Map<ContactGroupId, ContactGroup> contactGroups) {
        this.contactGroups = contactGroups;
    }

    @Override
    public ContactGroup save(ContactGroup contactGroup) throws EntityNotFoundException, ValidationException {

        // Error handling
        Map<String, ArrayList<String>> errors = new HashMap<>();
        errors.put("name", new ArrayList<>());
        errors.put("displayName", new ArrayList<>());

        if (contactGroup.getName() == null || contactGroup.getName().isEmpty()) errors.get("name").add("This field is required.");
        if (contactGroup.getDisplayName() == null || contactGroup.getDisplayName().isEmpty()) errors.get("displayName").add("This field is required.");

        for (Map.Entry<String, ArrayList<String>> entry : errors.entrySet()) {
            if (entry.getValue().size() != 0) throw new ValidationException(errors);
        }
        // End of error handling

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
