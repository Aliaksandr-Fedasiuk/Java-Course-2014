package com.epam.courses.dao;

import com.epam.courses.domain.User;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/testApplicationContextSpring.xml" })
public class UserDaoImplTest {

  @Autowired
  private UserDao userDao;

  @BeforeClass
  public static void setup() throws Exception {
    BasicConfigurator.configure();
    Logger.getRootLogger().setLevel(Level.DEBUG);
  }

  @Test
  public void getUsers() throws Exception {
    List<User> users = userDao.getUsers();
    assertNotNull(users);
    assertFalse(users.isEmpty());
  }

  @Test
  public void addUser() throws Exception {
    List<User> users = userDao.getUsers();
    int sizeBefore = users.size();
    User user = new User(3L, "userLogin3", "userName3");
    userDao.addUser(user);

    users = userDao.getUsers();
    assertEquals(sizeBefore, users.size() - 1);
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