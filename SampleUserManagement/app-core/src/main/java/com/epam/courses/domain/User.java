package com.epam.courses.domain;

/**
 *
 * @author afedasiuk on 10/15/14.
 */
public class User {

  private Long userId;

  private String login;

  private String name;

  public User() {
  }

  public User(Long userId, String login, String name) {
    this.userId = userId;
    this.login = login;
    this.name = name;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    User user = (User) o;

    if (login != null ? !login.equals(user.login) : user.login != null) {
      return false;
    }
    if (name != null ? !name.equals(user.name) : user.name != null) {
      return false;
    }
    if (userId != null ? !userId.equals(user.userId) : user.userId != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = userId != null ? userId.hashCode() : 0;
    result = 31 * result + (login != null ? login.hashCode() : 0);
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "User {" + "userId=" + userId + ", login='" + login + ", name='" + name + '}';
  }
}
