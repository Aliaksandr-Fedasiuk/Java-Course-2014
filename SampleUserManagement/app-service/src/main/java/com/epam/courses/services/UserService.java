package com.epam.courses.services;

import com.epam.courses.domain.User;

import java.util.List;

/**
 * Created by xalf on 10/23/14.
 */
public interface UserService {

  public void addUser(User user);

  public List<User> getAllUsers();

  public User getUserByLogin(String login);

  public void removeUser(Long userId);

}
