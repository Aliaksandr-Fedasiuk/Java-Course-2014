package com.epam.courses.dao;

import com.epam.courses.domain.User;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/testApplicationContextSpring.xml"})
public class UserDaoImplTest {

  private static final Logger LOGGER = Logger.getLogger(UserDaoImplTest.class);

  @Autowired
  private DataSource dataSource;

  @Autowired
  private ClassPathResource datasetResource;

  @Autowired
  private UserDao userDao;

  private User user;

  static {
    PropertyConfigurator.configure("src/test/resources/log4j.properties");
  }

  @BeforeClass
  public static void setup() throws Exception {
    BasicConfigurator.configure();
    Logger.getRootLogger().setLevel(Level.DEBUG);
  }

  @Before
  public void setUp() throws Exception {
    IDataSet dataSet = new FlatXmlDataSetBuilder().build(datasetResource.getInputStream());
    IDatabaseConnection dbConn = new DatabaseDataSourceConnection(dataSource);
    dbConn.getConnection().createStatement().executeUpdate(
        " create schema dbunit AUTHORIZATION dba; " +
        " commit; " +
        " drop table USER if exists; " +
        " create table USER (" +
        "   userid BIGINT IDENTITY, " +
        "   login VARCHAR(255) NOT NULL, " +
        "   name VARCHAR(255) NOT NULL); "
    );
    dbConn.getConnection().commit();
    DatabaseOperation.CLEAN_INSERT.execute(dbConn, dataSet);

    user = new User(1L, "userLogin", "userName");
  }

  @Test
  public void testGetUsers() throws Exception {
    List<User> users = userDao.getUsers();
    assertNotNull(users);

    int sizeBefore = users.size();
    userDao.addUser(user);

    users = userDao.getUsers();
    assertEquals(sizeBefore, users.size() - 1);
  }

}