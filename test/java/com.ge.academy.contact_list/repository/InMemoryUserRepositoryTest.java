package com.ge.academy.contact_list.repository;
import com.ge.academy.contact_list.entity.User;
import com.ge.academy.contact_list.entity.UserRole;
import com.ge.academy.contact_list.exception.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by 212393105 on 2016.06.14..
 */

public class InMemoryUserRepositoryTest {

    private InMemoryUserRepository inMemoryUserRepository;
    private HashMap userMap;
    private User admin;
    private User user1;
    private User user2;

    @Before
    public void setUp() {
        admin = new User("admin", "password", UserRole.ADMIN);
        user1 = new User("user1", "123456", UserRole.USER);
        user2 = new User("user2", "1234567", UserRole.USER);
        userMap = mock(HashMap.class);
        userMap = new HashMap<>();

        inMemoryUserRepository = new InMemoryUserRepository(userMap);
        userMap = mock(HashMap.class);
    }

    @Test
    public void findAllShouldReturnAllElementsOfInternalMap() {
        //Given data set in setUp() method

        userMap = mock(HashMap.class);

        List<User>returnedCollection = Arrays.asList(admin, user1, user2);

        when(userMap.values()).thenReturn(returnedCollection);

        inMemoryUserRepository = new InMemoryUserRepository(userMap);

        //When
        List<User> result = inMemoryUserRepository.findAll();

        //Then
        assertArrayEquals(returnedCollection.toArray(), result.toArray());
    }

    @Test
    public void findOneShouldReturnTheElementOfInternalMapWithGet() {
        //Given data set in setUp() method

        User gerzson = new User("Gerzson", "valami", UserRole.USER);

        when(userMap.get("Gerzson")).thenReturn(gerzson);

        inMemoryUserRepository = new InMemoryUserRepository(userMap);

        //When
        User result = inMemoryUserRepository.findOne("Gerzson");

        //Then
        verify(userMap).get("Gerzson");
        assertEquals(gerzson, result);
    }

    @Test
    public void saveShouldPutTheUserIntoTheMap() {
        //Given data set in setUp() method

        User origUser = new User("user3", "pipacs", UserRole.ADMIN);
        User newUser = new User("user3", "1234568", UserRole.USER);

        when(userMap.put("user3", newUser))
                .thenReturn(origUser);

        inMemoryUserRepository = new InMemoryUserRepository(userMap);

        //When
        User savedUser = inMemoryUserRepository.save(newUser);

        //Then
        verify(userMap).put("user3", newUser);
    }

    @Test
    public void saveShouldReturnWithTheSavedUser() {
        //Given data set in setUp() method

        User newUser = new User("user3", "1234568", UserRole.USER);;

        inMemoryUserRepository = new InMemoryUserRepository(userMap);

        //When
        User savedUser = inMemoryUserRepository.save(newUser);

        //Then
        assertEquals(newUser, savedUser);
    }

    @Test
    public void saveShouldReturnWithTheCopyOfSavedUser() {
        //Given data set in setUp() method

        User newUser = new User("user3", "1234568", UserRole.USER);

        inMemoryUserRepository = new InMemoryUserRepository(userMap);

        //When
        User savedUser = inMemoryUserRepository.save(newUser);

        //Then
        assertNotSame(newUser, savedUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveShouldThrowIllegalArgumentExceptionWhenUserNameIsNull() {
        //Given data set in setUp() method

        User newUserWithoutName = new User(null, "1234568", UserRole.USER);

        try {

            inMemoryUserRepository = new InMemoryUserRepository(userMap);

            //When
            inMemoryUserRepository.save(newUserWithoutName);

            //Then
        } finally {
            verify(userMap, Mockito.never()).put(any(),any());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveShouldThrowIllegalArgumentExceptionWhenUserNameIsEmpty() {
        //Given data set in setUp() method

        User newUserWithoutName = new User("", "1234568", UserRole.USER);

        try {

            inMemoryUserRepository = new InMemoryUserRepository(userMap);

            //When
            inMemoryUserRepository.save(newUserWithoutName);

            //Then
        } finally {
            verify(userMap, Mockito.never()).put(any(),any());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveShouldThrowIllegalArgumentExceptionWhenPasswordIsNull() {
        //Given data set in setUp() method

        User newUserWithoutName = new User("Valaki", null, UserRole.USER);

        try {

            inMemoryUserRepository = new InMemoryUserRepository(userMap);

            //When
            inMemoryUserRepository.save(newUserWithoutName);

            //Then
        } finally {
            verify(userMap, Mockito.never()).put(any(),any());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveShouldThrowIllegalArgumentExceptionWhenPasswordIsEmpty() {
        //Given data set in setUp() method

        User newUserWithoutName = new User("Valaki", "", UserRole.USER);

        try {

            inMemoryUserRepository = new InMemoryUserRepository(userMap);

            //When
            inMemoryUserRepository.save(newUserWithoutName);

            //Then
        } finally {
            verify(userMap, Mockito.never()).put(any(),any());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveShouldThrowIllegalArgumentExceptionWhenUserRoleIsNull() {
        //Given data set in setUp() method

        User newUserWithoutName = new User("Valaki", "12345", null);

        try {

            inMemoryUserRepository = new InMemoryUserRepository(userMap);

            //When
            inMemoryUserRepository.save(newUserWithoutName);

            //Then
        } finally {
            verify(userMap, Mockito.never()).put(any(),any());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteShouldThrowIllegalArgumentExceptionWhenParameterIsEmpty() {
        //Given data set in setUp() method

        try {

            inMemoryUserRepository = new InMemoryUserRepository(userMap);

            //When
            inMemoryUserRepository.delete("");

            //Then
        } finally {
            verify(userMap, Mockito.never()).put(any(),any());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteShouldThrowIllegalArgumentExceptionWhenParameterIsNull() {
        //Given data set in setUp() method

        try {

            inMemoryUserRepository = new InMemoryUserRepository(userMap);

            //When
            inMemoryUserRepository.delete(null);

            //Then
        } finally {
            verify(userMap, Mockito.never()).put(any(),any());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void findOneShouldThrowIllegalArgumentExceptionWhenParameterIsNull() {
        //Given data set in setUp() method

        try {

            inMemoryUserRepository = new InMemoryUserRepository(userMap);

            //When
            inMemoryUserRepository.findOne(null);

            //Then
        } finally {
            verify(userMap, Mockito.never()).put(any(),any());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void findOneShouldThrowIllegalArgumentExceptionWhenParameterIsEmpty() {
        //Given data set in setUp() method

        try {

            inMemoryUserRepository = new InMemoryUserRepository(userMap);

            //When
            inMemoryUserRepository.findOne("");

            //Then
        } finally {
            verify(userMap, Mockito.never()).put(any(),any());
        }
    }

    @Test
    public void deleteShouldRemoveUserFromMap() {
        //Given data set in setUp() method

        User origUser = new User("user3", "pipacs", UserRole.ADMIN);
        User newUser = new User("user3", "1234568", UserRole.USER);

        when(userMap.remove("admin"))
                .thenReturn(origUser);
        when(userMap.containsKey("admin"))
                .thenReturn(true);
        when(userMap.get("admin"))
                .thenReturn(origUser);

        inMemoryUserRepository = new InMemoryUserRepository(userMap);

        //When
        inMemoryUserRepository.delete("admin");

        //Then
        verify(userMap).remove("admin");
    }

    @Test
    public void deleteShouldThrowEntityNotFoundExceptionWhenEntityIsNotInMap() {
        //Given

        when(userMap.containsKey("admin")).thenReturn(false);
        when(userMap.get("admin")).thenReturn(null);
        when(userMap.remove("admin")).thenReturn(null);

        inMemoryUserRepository = new InMemoryUserRepository(userMap);
        try {
            //When

            inMemoryUserRepository.delete("admin");

            fail("Delete should throw an EntityNotFoundException");
        } catch (EntityNotFoundException ex) {
            assertEquals(User.class, ex.getEntityType());
            assertEquals("admin", ex.getEntityId());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveShouldThrowIllegalArgumentExceptionWhenUserNameAndPasswordIsNull() {
        //Given data set in setUp() method

        User newUserWithoutName = new User(null, null, UserRole.USER);

        try {

            inMemoryUserRepository = new InMemoryUserRepository(userMap);

            //When
            inMemoryUserRepository.save(newUserWithoutName);

            //Then
        } finally {
            verify(userMap, Mockito.never()).put(any(),any());
        }
    }


    @Test(expected = IllegalArgumentException.class)
    public void saveShouldThrowIllegalArgumentExceptionWhenUserNameAndPasswordAndUserRoleIsNull() {
        //Given data set in setUp() method

        User newUserWithoutName = new User(null, null, null);

        try {

            inMemoryUserRepository = new InMemoryUserRepository(userMap);

            //When
            inMemoryUserRepository.save(newUserWithoutName);

            //Then
        } finally {
            verify(userMap, Mockito.never()).put(any(),any());
        }
    }
}
