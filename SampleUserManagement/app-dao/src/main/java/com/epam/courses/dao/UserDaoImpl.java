package com.epam.courses.dao;

import com.epam.courses.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xalf on 10/17/14.
 */
@Repository
public class UserDaoImpl implements UserDao {

  private final static Logger LOGGER = LogManager.getLogger();

  public static final String USER_ID = "userid";

  public static final String LOGIN = "login";

  public static final String NAME = "name";

  @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${insert_into_user_path}')).file)}")
  public String addNewUserSql;

  private JdbcTemplate jdbcTemplate;

  private NamedParameterJdbcTemplate namedJdbcTemplate;

  public void setDataSource(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
    namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
  }

  @Override
  public Long addUser(User user) {
    LOGGER.debug("addUser({}) ", user);
    Assert.notNull(user);
    Assert.isNull(user.getUserId());
    Assert.notNull(user.getLogin(), "User login should be specified.");
    Assert.notNull(user.getName(), "User name should be specified.");
    Map<String, Object> parameters = new HashMap(3);
    parameters.put(NAME, user.getName());
    parameters.put(LOGIN, user.getLogin());
    parameters.put(USER_ID, user.getUserId());
    KeyHolder keyHolder = new GeneratedKeyHolder();
    namedJdbcTemplate.update(addNewUserSql, new MapSqlParameterSource(parameters), keyHolder);
    return keyHolder.getKey().longValue();

  }

  @Override
  public List<User> getUsers() {
    LOGGER.debug("getUsers()");
    return jdbcTemplate.query("select userId, login, name from USER", new UserMapper());
  }

  @Override
  public User getUserById(Long userId) {
    LOGGER.debug("getUserById :: userId = " + userId);
    return jdbcTemplate.queryForObject("select userId, login, name from USER where userid = ?",
        new UserMapper(), userId);
  }

  @Override
  public User getUserByLogin(String login) {
    return jdbcTemplate.queryForObject("select userId, login, name from USER where LCASE(login) = ?",
        new String[] { login.toLowerCase() }, new UserMapper());
  }

  @Override
  public void removeUser(Long userId) {
    LOGGER.debug("removeUser: userId = " + userId);
    jdbcTemplate.update("delete from USER where userId = ?", userId);
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
