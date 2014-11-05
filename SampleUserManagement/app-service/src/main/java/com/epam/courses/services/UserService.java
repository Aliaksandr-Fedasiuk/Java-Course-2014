package com.epam.courses.services;

import com.epam.courses.domain.User;

import java.util.List;

/**
 * Created by xalf on 10/23/14.
 */
public interface UserService {

  public Long addUser(User user);

  public List<User> getUsers();

  public User getUserByLogin(String login);

  public void removeUser(Long userId);

  public void updateUser(User user);

  public User getUserById(Long id);
}
