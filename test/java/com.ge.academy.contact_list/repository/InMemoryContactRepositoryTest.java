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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

/**
 * Created by 212564370 on 6/13/2016.
 */
public class InMemoryContactRepositoryTest {
    ContactRepository contactRepository;
    ConcurrentMap<ContactId, Contact> mockedMap;
    IdProvider mockedIdProvider;

    @BeforeMethod(alwaysRun = true)
    public void initContactRepository() {
        mockedIdProvider = mock(IdProvider.class);
        mockedMap = mock(ConcurrentHashMap.class);
        contactRepository = new InMemoryContactRepository(mockedIdProvider, mockedMap);
    }

    @Test
    public void saveShouldPutTheContactIntoTheMap() {
        //Given
        ContactId contactId = new ContactId("user1", "group1");
        Contact contact = new Contact(contactId, null, null, null, null, null, null);

        //When
        Contact result = contactRepository.save(contact);

        //Then
        verify(mockedMap).put(contactId, contact);
    }

    @Test
    public void saveShouldPutTheNewContactWithAGeneratedIdIntoMap() {
        //Given
        ContactId contactId = new ContactId("user1", "group1", 0);// contactId.id = 0 means create

        Contact contact = new Contact(contactId, "a", "b", "c", "d", "e", "f");
        long mockedNewId = 2345L;
        ContactId expectedNewId = new ContactId("user1", "group1", mockedNewId);
        Contact expectedCreatedContact = new Contact(expectedNewId, "a", "b", "c", "d", "e", "f");

        when(mockedIdProvider.getNewId())
                .thenReturn(mockedNewId);

        //When
        Contact result = contactRepository.save(contact);

        //Then
        //verify(mockedIdProvider).getNewId();

        verify(mockedMap).put(expectedNewId, expectedCreatedContact);
    }

    @Test
    public void saveShouldPutTheContactWithoutAGeneratedIdIntoMapWhenContactDoesExistInMap() {
        //Given
        ContactId contactId = new ContactId("user1", "group1", 1);
        Contact contact = new Contact(contactId, null, null, null, null, null, null);

        when(mockedMap.get(contactId)).thenReturn(contact);

        //When
        contactRepository.save(contact);

        //Then
        verify(mockedMap).put(contactId, contact);
    }

    @Test
    public void saveShouldThrowEntityNotFoundExceptionWhenContactDoesNotExistInMap() {
        //Given
        ContactId newContactId = new ContactId("user1", "group1", 1L);
        Contact newContact = new Contact(newContactId, null, null, null, null, null, null);

        when(mockedMap.get(anyObject())).thenReturn(null);
        when(mockedMap.containsKey(anyObject())).thenReturn(false);

        //When
        boolean wasExpectedException = false;

        //Then
        try {
            contactRepository.save(newContact);
            fail("Should throw EntityNotFoundException");
        } catch (EntityNotFoundException ex) {
            wasExpectedException = true;
            assertEquals(Contact.class, ex.getEntityType());
        }

        assertTrue(wasExpectedException, "Should throw EntityNotFoundException");
    }

    @Test
    public void deleteShouldRemoveTheContactFromMap() {
        //Given
        ContactId contactId = new ContactId("user1", "group1", 1L);
        Contact contact = new Contact(contactId, null, null, null, null, null, null);

        when(mockedMap.get(contactId)).thenReturn(contact);

        //When
        contactRepository.delete(contactId);

        //Then
        verify(mockedMap).remove(contactId);
    }

    @Test
    public void deleteShouldThrowEntityNotFoundExceptionWhenContactDoesNotExistInMap() {
        //Given
        ContactId contactId = new ContactId("user1", "group1", 1L);
        Contact contact = new Contact(contactId, null, null, null, null, null, null);

        when(mockedMap.get(anyObject())).thenReturn(null);
        when(mockedMap.containsKey(anyObject())).thenReturn(false);

        //When
        boolean wasExpectedException = false;

        //Then
        try {
            contactRepository.delete(contactId);
            fail("Should throw EntityNotFoundException");
        } catch (EntityNotFoundException ex) {
            wasExpectedException = true;
            assertEquals(Contact.class, ex.getEntityType());
        }

        assertTrue(wasExpectedException, "Should throw EntityNotFoundException");
    }

    @Test
    public void findOneShouldGetTheContactWithTheSameContactIdFromMap() {
        //Given
        ContactId contactId = new ContactId("user1", "group1", 1L);
        Contact contact = new Contact(contactId, null, null, null, null, null, null);

        when(mockedMap.get(contactId)).thenReturn(contact);

        //When
        Contact expected = contactRepository.findOne(contactId);

        //Then
        assertEquals(contact, expected);
    }

    @Test
    public void findOneShouldThrowEntityNotFoundExceptionWhenContactDoesNotExistInMap() {
        //Given
        ContactId contactId = new ContactId("user1", "group1", 1L);
        Contact contact = new Contact(contactId, null, null, null, null, null, null);

        when(mockedMap.get(anyObject())).thenReturn(null);
        when(mockedMap.containsKey(anyObject())).thenReturn(false);

        //When
        boolean wasExpectedException = false;

        //Then
        try {
            contactRepository.findOne(contactId);
            fail("Should throw EntityNotFoundException");
        } catch (EntityNotFoundException ex) {
            wasExpectedException = true;
            assertEquals(Contact.class, ex.getEntityType());
        }

        assertTrue(wasExpectedException, "Should throw EntityNotFoundException");
    }
}