package com.superbpm.platform.entity.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户
 */
@Entity
@Table(name = "SBP_USER")
public class User implements Serializable {

  @Id
  @Column(name = "D_UUID", nullable = false, length = 64)
  protected String uuid;

  @Id
  @Column(name = "D_USERNAME", nullable = false, length = 64)
  private String username; //用户名

  @Column(name = "D_PASSWORD", nullable = false, length = 255)
  private String password; //密码

  @Column(name = "D_SALT", nullable = false, length = 255)
  private String salt; //加密密码的盐

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public String getCredentialsSalt() {
    return username + salt;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;

    if (uuid != null ? !uuid.equals(user.uuid) : user.uuid != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return uuid != null ? uuid.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + uuid +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", salt='" + salt + '\'' +
            '}';
  }
}
