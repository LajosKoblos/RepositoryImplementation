package com.ge.academy.contact_list.repository;

import com.ge.academy.contact_list.entity.ContactGroup;
import com.ge.academy.contact_list.entity.ContactGroupId;
import com.ge.academy.contact_list.repository.ContactGroupRepository;
import com.ge.academy.contact_list.repository.InMemoryContactGroupRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Created by 212564432 on 6/14/2016.
 */
public class InMemoryContactGroupRepositoryTest {

    private ContactGroup testContactGroup;
    private ContactGroupId testContactGroupId;
    private InMemoryContactGroupRepository inMemoryContactGroupRepository;
    private Map<ContactGroupId, ContactGroup> contactGroups;

    @Before
    public void setUp() {

        contactGroups = mock(Map.class);
        testContactGroupId = new ContactGroupId("Test_User", "Test_Group_Id");
        testContactGroup = new ContactGroup("Test_Group", testContactGroupId);
        inMemoryContactGroupRepository = new InMemoryContactGroupRepository(contactGroups);
    }

    @Test
    public void saveShouldPutTheGivenContactGroupIntoMapWithItsIdAsTheKey() {
        // Given
        // When
        inMemoryContactGroupRepository.save(testContactGroup);
        // Then
        verify(contactGroups).put(testContactGroupId, testContactGroup);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveShouldThrowIllegalArgumentExceptionIfGroupNameIsNotProvided() {
        // Given
        ContactGroupId testContactGroupId = new ContactGroupId("testUser", null);
        ContactGroup testContactGroup = new ContactGroup("testcontactgroupname", testContactGroupId);
        // When
        inMemoryContactGroupRepository.save(testContactGroup);
        // Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveShouldThrowIllegalArgumentExceptionIfGroupNameIsEmptyString() {
        // Given
        ContactGroupId testContactGroupId = new ContactGroupId("testUser", new String(""));
        ContactGroup testContactGroup = new ContactGroup("testcontactgroupname", testContactGroupId);
        // When
        inMemoryContactGroupRepository.save(testContactGroup);
        // Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveShouldThrowIllegalArgumentExceptionIfIdIsNull() {
        // Given
        ContactGroup testContactGroup = new ContactGroup("testcontactgroupname", null);
        // When
        inMemoryContactGroupRepository.save(testContactGroup);
        // Then

    }

    @Test(expected = IllegalArgumentException.class)
    public void saveShouldThrowIllegalArgumentExceptionIfIdUserNameIsNull() {
        // Given
        ContactGroupId groupIdWithNullUserName = new ContactGroupId(null, "x");
        ContactGroup testContactGroup = new ContactGroup("testcontactgroupname", groupIdWithNullUserName);
        // When
        inMemoryContactGroupRepository.save(testContactGroup);
        // Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveShouldThrowIllegalArgumentExceptionIfIdUserNameIsEmptyString() {
        // Given
        ContactGroupId groupIdWithNullUserName = new ContactGroupId(new String(""), "x");
        ContactGroup testContactGroup = new ContactGroup("testcontactgroupname", groupIdWithNullUserName);
        // When
        inMemoryContactGroupRepository.save(testContactGroup);
        // Then
    }

}



