package com.epam.courses.dao;

import com.epam.courses.domain.User;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by xalf on 10/17/14.
 */
@Repository
public class UserDaoImpl implements UserDao {

  private final static Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

  private JdbcTemplate jdbcTemplate;

  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public void addUser(User user) {
    LOGGER.debug("add: " + user);
    jdbcTemplate.update("insert into USER(userid, login, name) values(?, ?, ?)",
        new Object[] {user.getUserId(), user.getLogin(), user.getName()});
  }

  @Override
  public List<User> getUsers() {
    LOGGER.debug("get users()");
    return jdbcTemplate.query( "select userId, login, name from USER", new UserMapper());
  }

  @Override
  public void removeUser(Long userId) {
    LOGGER.debug("removeUser: userId = " + userId);
    jdbcTemplate.update("delete from USER where userId = ?", Long.valueOf(userId));
  }

  public class UserMapper implements RowMapper<User> {

    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
      User user = new User();
      user.setUserId(rs.getLong("userId"));
      user.setLogin(rs.getString("login"));
      user.setName(rs.getString("name"));
      return user;
    }
  }

}
