package com.epam.courses.services;

import com.epam.courses.dao.UserDao;
import com.epam.courses.domain.User;
import org.junit.After;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

public class UserServiceImplMockTest {

    private UserDao userDao;

    private UserServiceImpl userService;

    public UserServiceImplMockTest() {
        userDao = createMock(UserDao.class);
        userService = new UserServiceImpl();
        userService.setUserDao(userDao);
    }

    @After
    public void clean() {
        reset(userDao);
    }

    @Test
    public void addUser() {
        User user = UserDataFixture.getNewUser();

        userDao.getUserByLogin(user.getLogin());
        expectLastCall().andReturn(null);
        userDao.addUser(user);
        expectLastCall();
        replay(userDao);

        userService.addUser(user);

        verify(userDao);
    }

    @Test
    public void addUser2() {
        User user = UserDataFixture.getNewUser();

        expect(userDao.getUserByLogin(user.getLogin())).andReturn(null).times(2);
        userDao.addUser(user);
        expectLastCall().times(2);
        replay(userDao);

        userService.addUser(user);
        userService.addUser(user);

        verify(userDao);
    }

    @Test
    public void getUserByLogin() {
        User user = UserDataFixture.getExistUser(1);

        expect(userDao.getUserByLogin(user.getLogin())).andReturn(user);
        replay(userDao);

        User result = userService.getUserByLogin(user.getLogin());

        verify(userDao);

        assertSame(user, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addUserWithSameLogin() {
        User user = UserDataFixture.getNewUser();

        expect(userDao.getUserByLogin(user.getLogin())).andReturn(null);
        userDao.addUser(user);
        expectLastCall();
        expect(userDao.getUserByLogin(user.getLogin())).andReturn(user);
        replay(userDao);

        userService.addUser(user);
        userService.addUser(user);
    }

    @Test(expected = NumberFormatException.class)
    public void addUserException() {
        User user = UserDataFixture.getNewUser();

        expect(userDao.getUserByLogin(user.getLogin())).andThrow(new NumberFormatException());
        replay(userDao);

        userService.addUser(user);
    }
}