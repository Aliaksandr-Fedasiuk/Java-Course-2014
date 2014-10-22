package com.epam.courses.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UserTest extends Assert {

    private final List<Integer> TEST_DATA = new ArrayList<Integer>();

    private User user;

    @Before
    public void setUpTestData() {
        TEST_DATA.add(42);
        user = new User();
    }

    @Test
    public void testGetUserId() throws Exception {
        user.setUserId(1L);
        assertEquals(1L, user.getUserId().longValue());
    }

    @Test
    public void testGetLogin() throws Exception {
        user.setLogin("User Login");
        assertEquals("User Login", user.getLogin());
    }

    @Test
    public void testGetName() throws Exception {
        user.setName("User Name");
        assertEquals("User Name", user.getName());
    }

    @Test(expected = NullPointerException.class)
    public void testForNPEAnnotation() {
        throw new NullPointerException();
    }

    @After
    public void cleanUpTestData() {
        TEST_DATA.clear();
    }

}