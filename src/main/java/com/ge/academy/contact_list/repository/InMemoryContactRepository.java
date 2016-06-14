package com.ge.academy.contact_list.repository;

import com.ge.academy.contact_list.entity.Contact;
import com.ge.academy.contact_list.entity.ContactGroupId;
import com.ge.academy.contact_list.entity.ContactId;
import com.ge.academy.contact_list.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

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
    public Contact save(Contact contact) throws EntityNotFoundException {
        Contact managed = new Contact(contact);
        ContactId managedId = managed.getId();
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

    }

    @Override
    public Contact findOne(ContactId contactId) throws EntityNotFoundException {
        return null;
    }

    @Override
    public List<Contact> findAll() {
        return null;
    }

    @Override
    public List<Contact> findByExample(Contact contact) throws EntityNotFoundException {
        return null;
    }

    @Override
    public List<Contact> findByContactGroupId(ContactGroupId contactGroupId) throws EntityNotFoundException {
        return null;
    }
}
