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
    private AtomicLong counter = new AtomicLong(0);
    private ConcurrentMap<ContactId, Contact> contacts;

    public InMemoryContactRepository() {
        this.contacts = new ConcurrentHashMap<ContactId, Contact>();
    }

    public InMemoryContactRepository(ConcurrentMap<ContactId, Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public Contact save(Contact contact) throws EntityNotFoundException {
        Contact managed = new Contact(contact);
        ContactId managedId = managed.getId();
        if (managedId.getContactId() == 0L) {
            managedId.setContactId(counter.incrementAndGet());
        } else if (contacts.get(managedId) == null) {
            throw new EntityNotFoundException(Contact.class, managed.getId());
        }
        return new Contact(contacts.put(managedId, managed));
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
    public Contact findByExample(Contact contact) throws EntityNotFoundException {
        return null;
    }

    @Override
    public List<Contact> findByContactGroupId(ContactGroupId contactGroupId) throws EntityNotFoundException {
        return null;
    }
}
