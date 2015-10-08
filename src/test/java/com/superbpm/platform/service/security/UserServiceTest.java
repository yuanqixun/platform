package com.superbpm.platform.service.security;

import com.superbpm.platform.config.PlatformConfig;
import com.superbpm.platform.config.WebMvcConfig;
import com.superbpm.platform.entity.security.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by yqx on 9/22/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {PlatformConfig.class},inheritInitializers = false)
@EnableAutoConfiguration(exclude = WebMvcConfig.class)
public class UserServiceTest {

  @Autowired
  UserService userService;

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void testCreateUser() throws Exception {
    User user = new User();
    user.setUsername("admin");
    user.setPassword("123456");
    user = userService.createUser(user);
    Assert.assertNotNull(user.getUuid());
  }

  @Test
  public void testUpdateUser() throws Exception {

  }

  @Test
  public void testDeleteUser() throws Exception {

  }

  @Test
  public void testChangePassword() throws Exception {

  }

  @Test
  public void testFindOne() throws Exception {
    User user = userService.findOne("f6cd53fc-018c-4587-bd2a-494a7bfcd549");
    org.junit.Assert.assertNotNull(user);
  }

  @Test
  public void testFindAll() throws Exception {
    List<User> list  = userService.findAll();
    org.junit.Assert.assertNotNull(list);
    for(User user:list){
      System.out.println(user);
    }
  }

  @Test
  public void testFindByUsername() throws Exception {
      User user = userService.findByUsername("yuanqixun");
    org.junit.Assert.assertNotNull(user);

  }

  @Test
  public void testCheckUser() throws Exception {
    String username = "admin";
    User user = userService.findByUsername("admin");
    Assert.assertNotNull(user);

    String salt = user.getSalt();
    String password = "123456";
    String encryptpassword=user.getPassword();

    boolean check = userService.checkUser(username,password,salt,encryptpassword);
    Assert.assertTrue(check);
  }
}