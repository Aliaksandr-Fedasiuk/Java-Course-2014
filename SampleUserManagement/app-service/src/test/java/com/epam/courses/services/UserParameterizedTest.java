package com.epam.courses.services;

import com.epam.courses.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
@ContextConfiguration(locations = {"classpath*:/testServiceApplicationContextSpring.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class UserParameterizedTest {

    @Autowired
    private UserService userService;

    private User user;

    public UserParameterizedTest(User user) {
        this.user = user;
    }

    @Before
    public void setUp() throws Exception {
        TestContextManager testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);
    }

    @Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][]{
                {null},
                {new User()},
                {new User(12L, "", "")}
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void test() {
        userService.addUser(user);
    }
}
