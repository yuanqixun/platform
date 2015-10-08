package com.superbpm.platform.dao.security;

import com.superbpm.platform.entity.security.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserDao {

  @Insert("insert into SBP_USER(d_uuid,d_username, d_password, d_salt) values(#{uuid},#{username},#{password},#{salt})")
  int createUser(final User user);

  @Update("update SBP_USER set d_username=#{username}, d_password=#{password}, d_salt=#{salt} where d_uuid=#{uuid}")
  int updateUser(User user);

  @Delete("delete from SBP_USER where d_uuid=#{uuid}")
  int deleteUser(String uuid);

  @Select("select * from SBP_USER where D_UUID=#{uuid} ")
  @ResultMap({"User"})
  @One
  User findOne(String uuid);

  @Select("select * from SBP_USER")
  @ResultMap({"User"})
  @Many
  List<User> findAll();

  @Select("select * from SBP_USER where d_username=#{username}")
  @ResultMap({"User"})
  @Many
  User findByUsername(String username);

}
