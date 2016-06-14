package com.ge.academy.contact_list.repository;

import com.ge.academy.contact_list.entity.Contact;
import com.ge.academy.contact_list.entity.ContactId;
import com.ge.academy.contact_list.exception.EntityNotFoundException;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.internal.annotations.ExpectedExceptionsAnnotation;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

/**
 * Created by 212564370 on 6/13/2016.
 */
public class InMemoryContactRepositoryTest {
    ContactRepository contactRepository;
    ConcurrentMap<ContactId, Contact> mockedMap;

    @BeforeMethod(alwaysRun = true)
    public void initContactRepository() {
        mockedMap = mock(ConcurrentHashMap.class);
        contactRepository = new InMemoryContactRepository(mockedMap);
    }

    @Test
    public void saveNewContact() {
        //Given
        ContactId contactId = new ContactId("user1", "group1");
        Contact contact = new Contact(contactId, null, null, null, null, null, null);

        when(mockedMap.put(anyObject(), anyObject())).then(AdditionalAnswers.returnsLastArg());

        long expected = 1L;

        //When
        long result = contactRepository.save(contact).getId().getContactId();

        //Then
        assertEquals(result, expected);
    }

    @Test
    public void saveExistingContact() {
        //Given
        ContactId newContactId = new ContactId("user1", "group1");
        Contact newContact = new Contact(newContactId, null, null, null, null, null, null);

        when(mockedMap.put(anyObject(), anyObject())).then(AdditionalAnswers.returnsLastArg());
        when(mockedMap.get(anyObject())).then(returnsFirstArg());

        Contact expected = contactRepository.save(newContact);

        //When
        Contact result = contactRepository.save(expected);

        //Then
        assertEquals(result, expected);
    }

    @Test(expectedExceptions = {EntityNotFoundException.class})
    public void saveNonExistingContact() {
        //Given
        ContactId newContactId = new ContactId("user1", "group1", 1L);
        Contact newContact = new Contact(newContactId, null, null, null, null, null, null);

        when(mockedMap.get(anyObject())).thenReturn(null);

        //When
        contactRepository.save(newContact);

        //Then
    }
}