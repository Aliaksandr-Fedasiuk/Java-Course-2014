package com.epam.courses.services;

import com.epam.courses.dao.UserDao;
import com.epam.courses.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;


/**
 * Created by xalf on 10/23/14.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

  private final static Logger LOGGER = LogManager.getLogger();

  @Autowired
  private UserDao userDao;

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public Long addUser(User user) {
    LOGGER.debug("addUser :: " + user);
    Assert.notNull(user, "Object User should not be null.");
    Assert.isNull(user.getUserId());
    Assert.notNull(user.getLogin(), "User login should not be empty.");
    Assert.notNull(user.getName(), "User name should not be empty.");
    User existingUser = getUserByLogin(user.getLogin());
    if (existingUser != null) {
      throw new IllegalArgumentException("Duplicate object User in DB with same login.");
    }
    return userDao.addUser(user);
  }

  @Override
  public List<User> getUsers() {
    LOGGER.debug("getUsers()");
    return userDao.getUsers();
  }

  @Override
  public User getUserByLogin(String login) {
    LOGGER.debug("getUserByLogin :: login = " + login);
    User user = null;
    try {
      user = userDao.getUserByLogin(login);
    } catch (EmptyResultDataAccessException e) {
      LOGGER.debug("Empty result for getUserByLogin :: login = " + login);
    }
    return user;
  }

  @Override
  public User getUserById(Long id) {
    return null;
  }

  @Override
  public void updateUser(User user) {

  }

  @Override
  public void removeUser(Long userId) {
    LOGGER.debug("removeUser :: userId = " + userId);
    userDao.removeUser(userId);
  }
}
