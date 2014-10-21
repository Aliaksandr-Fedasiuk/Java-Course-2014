package com.epam.courses.dao;

import com.epam.courses.domain.User;

import java.util.List;

/**
 * Created by xalf on 10/17/14.
 */
public interface UserDao {

  public void addUser(User user);

  public List<User> getUsers();

  public void removeUser(Long userId);

}
