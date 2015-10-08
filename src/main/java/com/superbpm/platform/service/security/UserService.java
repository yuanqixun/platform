package com.superbpm.platform.service.security;

import com.superbpm.platform.dao.security.UserDao;
import com.superbpm.platform.entity.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class UserService {
  @Autowired
  private UserDao userDao;

  @Autowired
  private PasswordHelper passwordHelper;

  /**
   * 创建用户
   *
   * @param user
   */
  public User createUser(User user) {
    user.setUuid(UUID.randomUUID().toString());
    //加密密码
    passwordHelper.encryptPassword(user);
    userDao.createUser(user);
    return user;
  }

  public User updateUser(User user) {
    userDao.updateUser(user);
    return userDao.findOne(user.getUuid());
  }

  public void deleteUser(String userId) {
    userDao.deleteUser(userId);
  }

  /**
   * 修改密码
   *
   * @param userId
   * @param newPassword
   */
  public void changePassword(String userId, String newPassword) {
    User user = userDao.findOne(userId);
    user.setPassword(newPassword);
    passwordHelper.encryptPassword(user);
    userDao.updateUser(user);
  }

  public User findOne(String uuid) {
    return userDao.findOne(uuid);
  }

  public List<User> findAll() {
    return userDao.findAll();
  }

  /**
   * 根据用户名查找用户
   *
   * @param username
   * @return
   */
  public User findByUsername(String username) {
    return userDao.findByUsername(username);
  }

  /**
   * 验证登录
   *
   * @param username   用户名
   * @param password   密码
   * @param salt       盐
   * @param encryptpwd 加密后的密码
   * @return
   */
  public boolean checkUser(String username, String password, String salt, String encryptpwd) {
    String pwd = passwordHelper.encryptPassword(username, password, salt);
    return pwd.equals(encryptpwd);
  }


}
