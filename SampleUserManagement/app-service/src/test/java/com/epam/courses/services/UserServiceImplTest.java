package com.epam.courses.services;

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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/testServiceApplicationContextSpring.xml" })
public class UserServiceImplTest {

  public static final String USER_NAME = "userName";

  public static final String USER_LOGIN = "userLogin";

  public static final String USER_NAME_TEST = "userNameTest";

  public static final String USER_LOGIN_TEST = "userLoginTest";

  @BeforeClass
  public static void setup() throws Exception {
    BasicConfigurator.configure();
    Logger.getRootLogger().setLevel(Level.DEBUG);
  }

  @Autowired
  private UserService userService;

  @Autowired
  private TransactionTemplate transactionTemplate;

  @Test(expected = IllegalArgumentException.class)
  public void testAddNullUser() throws Exception {
    userService.addUser(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddNotNullIdUser() throws Exception {
    userService.addUser(new User(1000L, USER_LOGIN, USER_NAME));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddNullLoginUser() throws Exception {
    userService.addUser(new User(null, null, USER_NAME));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddNullNameUser() throws Exception {
    userService.addUser(new User(null, USER_LOGIN, null));
  }

  @Test
  public void testAddUser() throws Exception {
    List<User> users = userService.getAllUsers();
    User newUser = new User(null, USER_LOGIN_TEST, "userNameTest");
    userService.addUser(newUser);
    assertEquals(users.size(), userService.getAllUsers().size() - 1);
    assertEquals(userService.getUserByLogin(USER_LOGIN_TEST).getLogin(), USER_LOGIN_TEST);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddUserWithSameLogin() throws Exception {
    List<User> users = userService.getAllUsers();
    assertTrue(users.size() > 0);
    User user = users.get(0);
    user.setUserId(null);
    userService.addUser(user);
  }

  @Test
  public void testAddUserInTransaction() {
    List<User> users = userService.getAllUsers();
    transactionTemplate.execute(new TransactionCallback<Void>() {
      @Override
      public Void doInTransaction(TransactionStatus transactionStatus) {
        User newUser = new User(null, USER_LOGIN_TEST + 01, USER_NAME_TEST + 01);
        userService.addUser(newUser);
        return null;
      }
    });
    assertEquals(users.size(), userService.getAllUsers().size() - 1);
    assertEquals(userService.getUserByLogin(USER_LOGIN_TEST).getLogin(), USER_LOGIN_TEST);
  }

  @Test
  public void testAddUserTransactionRollback() {
    List<User> users = userService.getAllUsers();
    transactionTemplate.execute(new TransactionCallback<Void>() {
      @Override
      public Void doInTransaction(TransactionStatus transactionStatus) {
        try {
          User newUser = new User(null, USER_LOGIN_TEST + 02, USER_NAME_TEST + 02);
          userService.addUser(newUser);
          userService.addUser(newUser);
        } catch (Exception e) {
          transactionStatus.setRollbackOnly();
        }
        return null;
      }
    });
    assertEquals(users.size(), userService.getAllUsers().size());
    assertNull(userService.getUserByLogin(USER_LOGIN_TEST + 02));
  }

}