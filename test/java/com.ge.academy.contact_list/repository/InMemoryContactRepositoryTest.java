package com.ge.academy.contact_list.repository;

import com.ge.academy.contact_list.entity.Contact;
import org.mockito.Mock;
import org.testng.annotations.Test;

import java.util.concurrent.ConcurrentMap;

import static org.testng.Assert.*;

/**
 * Created by 212564370 on 6/13/2016.
 */
public class InMemoryContactRepositoryTest {
    @Mock
    ConcurrentMap<String, Contact> mockedMap;

    @Test
    public void save() {

    }
}