package com.ge.academy.contact_list.repository;

import com.ge.academy.contact_list.entity.ContactGroup;
import com.ge.academy.contact_list.entity.ContactGroupId;
import com.ge.academy.contact_list.repository.ContactGroupRepository;
import com.ge.academy.contact_list.repository.InMemoryContactGroupRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.*;

/**
 * Created by 212564432 on 6/14/2016.
 */
public class InMemoryContactGroupRepositoryTest {

    private List<ContactGroup> contactGroups;
    private List<ContactGroup> spiedContactGroups;
    private ContactGroup testContactGroup;
    private ContactGroupId testContactGroupId;
    private InMemoryContactGroupRepository inMemoryContactGroupRepository;

    @org.testng.annotations.BeforeMethod
    public void setUp() throws Exception {

        contactGroups = new ArrayList<>();
        spiedContactGroups = spy(contactGroups);
        testContactGroupId = new ContactGroupId("Test_User", "Test_Group_Id");
        testContactGroup = new ContactGroup("Test_Group", testContactGroupId);
        inMemoryContactGroupRepository = new InMemoryContactGroupRepository();
    }

    @org.testng.annotations.AfterMethod
    public void tearDown() throws Exception {

    }

    @org.testng.annotations.Test
    public void testSave() throws Exception {
        System.out.println("test_Saave");
        // Given
        // When
//        inMemoryContactGroupRepository.save(testContactGroup);
        // Then
//        verify(spiedContactGroups).add(testContactGroup);

    }

    @org.testng.annotations.Test
    public void testDelete() throws Exception {

    }

    @org.testng.annotations.Test
    public void testFindOne() throws Exception {

    }

    @org.testng.annotations.Test
    public void testFindAll() throws Exception {

    }

    @org.testng.annotations.Test
    public void testFindByOwner() throws Exception {

    }

}