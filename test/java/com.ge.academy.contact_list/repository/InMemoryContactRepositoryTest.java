package com.ge.academy.contact_list.repository;

import com.ge.academy.contact_list.entity.Contact;
import com.ge.academy.contact_list.entity.ContactGroupId;
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

import java.util.ArrayList;
import java.util.List;
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
        when(mockedIdProvider.getNewId())
                .thenReturn(mockedNewId);

        //When
        Contact result = contactRepository.save(contact);

        //Then

        ContactId expectedNewId = new ContactId("user1", "group1", mockedNewId);
        Contact expectedCreatedContact = new Contact(expectedNewId, "a", "b", "c", "d", "e", "f");

        assertEquals(0, contactId.getContactId());
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

    @Test
    public void findAllShouldReturnTwoContactsWhenTwoContactInTheRepository() {
        //Given
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact(new ContactId("user1", "group1", 1), "1abc", "1bcd", "1cde", "1def", "1efg", "1fgh"));
        contacts.add(new Contact(new ContactId("user1", "group1", 2), "2abc", "2bcd", "2cde", "2def", "2efg", "2fgh"));

        when(mockedMap.size()).thenReturn(contacts.size());
        when(mockedMap.values()).thenReturn(contacts);

        //When
        List<Contact> result = contactRepository.findAll();

        //Then
        assertEquals(contacts, result);
    }

    @Test
    public void findAllShouldReturnWithDifferentReferenceOfTheContacts() {
        //Given
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact(new ContactId("user1", "group1", 1), "1abc", "1bcd", "1cde", "1def", "1efg", "1fgh"));

        when(mockedMap.size()).thenReturn(contacts.size());
        when(mockedMap.values()).thenReturn(contacts);

        Contact expected = contacts.get(0);

        //When
        List<Contact> resultList = contactRepository.findAll();
        Contact result = resultList.get(0);

        //Then
        assertNotSame(expected, result);
    }

    @Test
    public void findByExampleShouldFindOneContactWhenJustOneContactMatch() {
        //Given
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact(new ContactId("user1", "group1", 1), "1abc", "1bcd", "1cde", "1def", "1efg", "1fgh"));
        contacts.add(new Contact(new ContactId("user1", "group1", 2), "2abc", "2bcd", "2cde", "2def", "2efg", "2fgh"));

        Contact example = new Contact(new ContactId("user1", "", 0), "1abc", "1bcd", "1cde", "1def", "1efg", "1fgh");

        when(mockedMap.values()).thenReturn(contacts);

        int expected = 1;

        //When
        List<Contact> resultList = contactRepository.findByExample(example);
        int result = resultList.size();

        //Then
        assertEquals(result, expected);
    }

    @Test
    public void findByExampleShouldFindNothingWhenThereIsNoContactForThisUser() {
        //Given
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact(new ContactId("user2", "group1", 1), "1abc", "1bcd", "1cde", "1def", "1efg", "1fgh"));

        Contact example = new Contact(new ContactId("user1", "", 0), "1abc", "1bcd", "1cde", "1def", "1efg", "1fgh");

        when(mockedMap.values()).thenReturn(contacts);

        int expected = 0;

        //When
        List<Contact> resultList = contactRepository.findByExample(example);
        int result = resultList.size();

        //Then
        assertEquals(result, expected);
    }

    @Test
    public void findByContactGroupIdShouldFindTwoContactsWhenTwoContactsInTheGivenGroup() {
        //Given
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact(new ContactId("user1", "group1", 1), "1abc", "1bcd", "1cde", "1def", "1efg", "1fgh"));
        contacts.add(new Contact(new ContactId("user1", "group1", 2), "2abc", "2bcd", "2cde", "2def", "2efg", "2fgh"));
        contacts.add(new Contact(new ContactId("user1", "group2", 3), "3abc", "3bcd", "3cde", "3def", "3efg", "3fgh"));
        contacts.add(new Contact(new ContactId("user2", "group1", 4), "1abc", "1bcd", "1cde", "1def", "1efg", "1fgh"));

        Contact example = new Contact(new ContactId("user1", "", 0), "1abc", "1bcd", "1cde", "1def", "1efg", "1fgh");

        when(mockedMap.values()).thenReturn(contacts);

        ContactGroupId contactGroupId = new ContactGroupId("user1", "group1");

        int expected = 2;

        //When
        List<Contact> resultList = contactRepository.findByContactGroupId(contactGroupId);
        int result = resultList.size();

        //Then
        assertEquals(result, expected);
    }

    @Test
    public void findByContactGroupIdShouldFindNothingWhenThereIsNoContactForTheGivenContactGroup() {
        //Given
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact(new ContactId("user1", "group1", 1), "1abc", "1bcd", "1cde", "1def", "1efg", "1fgh"));
        contacts.add(new Contact(new ContactId("user1", "group1", 2), "2abc", "2bcd", "2cde", "2def", "2efg", "2fgh"));
        contacts.add(new Contact(new ContactId("user1", "group2", 3), "3abc", "3bcd", "3cde", "3def", "3efg", "3fgh"));
        contacts.add(new Contact(new ContactId("user2", "group1", 4), "1abc", "1bcd", "1cde", "1def", "1efg", "1fgh"));

        Contact example = new Contact(new ContactId("user1", "", 0), "1abc", "1bcd", "1cde", "1def", "1efg", "1fgh");

        when(mockedMap.values()).thenReturn(contacts);

        ContactGroupId contactGroupId = new ContactGroupId("user1", "group3");

        int expected = 0;

        //When
        List<Contact> resultList = contactRepository.findByContactGroupId(contactGroupId);
        int result = resultList.size();

        //Then
        assertEquals(result, expected);
    }
}