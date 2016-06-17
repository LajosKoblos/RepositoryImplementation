package com.ge.academy.contact_list.repository;

import com.ge.academy.contact_list.entity.Contact;
import com.ge.academy.contact_list.entity.ContactGroupId;
import com.ge.academy.contact_list.entity.ContactId;
import com.ge.academy.contact_list.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryContactRepository implements ContactRepository {
    private IdProvider idProvider;
    private ConcurrentMap<ContactId, Contact> contacts;

    public InMemoryContactRepository() {
        this.idProvider = new AtomicLongIdProvider();
        this.contacts = new ConcurrentHashMap<ContactId, Contact>();
    }

    public InMemoryContactRepository(IdProvider idProvider, ConcurrentMap<ContactId, Contact> contacts) {
        this.idProvider = idProvider;
        this.contacts = contacts;
    }

    @Override
    public Contact save(Contact contact) throws EntityNotFoundException, IllegalArgumentException {
        Contact managed = new Contact(contact);
        ContactId managedId = managed.getId();

        if (managedId.getContactGroupName().isEmpty()) {
            throw new IllegalArgumentException("contactGroupName is empty in ContactId");
        }
        if (managedId.getUserName().isEmpty()) {
            throw new IllegalArgumentException("userName is empty in ContactId");
        }

        if (managedId.getContactId() == 0L) {
            managedId.setContactId(idProvider.getNewId());
        } else if (contacts.get(managedId) == null) {
            throw new EntityNotFoundException(Contact.class, managed.getId());
        }
        contacts.put(managedId, managed);
        return new Contact(managed);
    }

    @Override
    public void delete(ContactId contactId) throws EntityNotFoundException {
        if (contacts.get(contactId) == null) {
            throw new EntityNotFoundException(Contact.class, contactId);
        }
        contacts.remove(contactId);
    }

    @Override
    public Contact findOne(ContactId contactId) throws EntityNotFoundException {
        Contact contact = contacts.get(contactId);
        if (contact == null) {
            throw new EntityNotFoundException(Contact.class, contactId);
        }
        return new Contact(contact);
    }

    @Override
    public List<Contact> findAll() {
        List<Contact> contactsList = new ArrayList<>();
        Contact[] originalContacts = new Contact[contacts.size()];
        contacts.values().toArray(originalContacts);

        for (Contact c:originalContacts) {
            contactsList.add(new Contact(c));
        }

        return contactsList;
    }

    @Override
    public List<Contact> findByExample(Contact contact) throws EntityNotFoundException {
        List<Contact> matched = new ArrayList<>();

        for (Contact c : contacts.values()) {
            if (c.getId() == null || contact.getId() == null || !c.getId().getUserName().equals(contact.getId().getUserName())) {
                continue;
            }
            if ((c.getFirstName() != null && contact.getFirstName() != null && c.getFirstName().toLowerCase().contains(contact.getFirstName().toLowerCase())) ||
                    (c.getHomeEmail() != null && contact.getHomeEmail() != null && c.getHomeEmail().toLowerCase().contains(contact.getHomeEmail().toLowerCase())) ||
                    (c.getJobTitle() != null && contact.getJobTitle() != null && c.getJobTitle().toLowerCase().contains(contact.getHomeEmail().toLowerCase())) ||
                    (c.getLastName() != null && contact.getLastName() != null && c.getLastName().toLowerCase().contains(contact.getLastName().toLowerCase())) ||
                    (c.getNickName() != null && contact.getNickName() != null && c.getNickName().toLowerCase().contains(contact.getNickName().toLowerCase())) ||
                    (c.getWorkEmail() != null && contact.getWorkEmail() != null && c.getWorkEmail().toLowerCase().contains(contact.getWorkEmail().toLowerCase()))) {
                matched.add(new Contact(c));
            }
        }

        return matched;
    }

    @Override
    public List<Contact> findByContactGroupId(ContactGroupId contactGroupId) throws EntityNotFoundException {
        List<Contact> matched = new ArrayList<>();

        for (Contact c : contacts.values()) {
            ContactId cId = c.getId();
            if (cId.getUserName().equals(contactGroupId.getUserName()) &&
                    cId.getContactGroupName().equals(contactGroupId.getContactGroupName())) {
                matched.add(new Contact(c));
            }
        }

        return matched;
    }
}
