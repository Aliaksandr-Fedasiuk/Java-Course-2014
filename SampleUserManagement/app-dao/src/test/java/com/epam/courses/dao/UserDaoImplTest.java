package com.epam.courses.dao;

import com.epam.courses.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/testDaoApplicationContextSpring.xml" })
public class UserDaoImplTest {

  @Autowired
  private UserDao userDao;

  @Test
  public void getUsers() throws Exception {
    List<User> users = userDao.getUsers();
    assertNotNull(users);
    assertFalse(users.isEmpty());
  }

  @Test
  public void testAddUser() throws Exception {
    List<User> users = userDao.getUsers();
    int sizeBefore = users.size();
    User user = new User(null, "userLogin3", "userName3");
    userDao.addUser(user);

    users = userDao.getUsers();
    assertEquals(sizeBefore, users.size() - 1);
  }

  @Test
  public void testGetUserByLogin() throws Exception {
    User user = new User(null, "userLogin6", "userName6");
    Long userId = userDao.addUser(user);

    User newUser = userDao.getUserByLogin("userLogin6");
    user.setUserId(userId);
    assertEquals(user, newUser);
  }

  @Test
  public void testGetUserById() throws Exception {
    User user = new User(null, "userLogin4", "userName4");
    Long userId = userDao.addUser(user);

    User newUser = userDao.getUserById(userId);
    user.setUserId(userId);
    assertEquals(user, newUser);
  }

  @Test
  public void removeUser() throws Exception {
    List<User> users = userDao.getUsers();
    int sizeBefore = users.size();
    userDao.removeUser(2L);
    users = userDao.getUsers();
    assertEquals(sizeBefore, users.size() + 1);
  }
}