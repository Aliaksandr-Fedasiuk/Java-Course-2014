package com.epam.courses.services;

import com.epam.courses.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/testServiceApplicationContextSpring.xml"})
public class UserServiceImplTest {

  @Autowired
  private UserService userService;

  @Test(expected = IllegalArgumentException.class)
  public void testAddNullUser() throws Exception {
    userService.addUser(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddNotNullIdUser() throws Exception {
    userService.addUser(new User(1000L, "userLogin", "userName"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddNullLoginUser() throws Exception {
    userService.addUser(new User(null, null, "userName"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddNullNameUser() throws Exception {
    userService.addUser(new User(null, "userLogin", null));
  }

  @Test
  public void testAddUser() throws Exception {
    List<User> users = userService.getAllUsers();
    User newUser = new User(null, "userLoginTest", "userNameTest");
    userService.addUser(newUser);
    assertEquals(users.size(), userService.getAllUsers().size() - 1);
    assertEquals(userService.getUserByLogin("userLoginTest").getLogin(), "userLoginTest");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddUserWithSameLogin() throws Exception {
    List<User> users = userService.getAllUsers();
    assertTrue(users.size() > 0);
    User user = users.get(0);
    user.setUserId(null);
    userService.addUser(user);
  }

}