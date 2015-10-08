package com.superbpm.platform.dao.oauth2;

import com.superbpm.platform.entity.oauth2.Client;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ClientDao {

  @Insert("insert into SPB_AUTH_CLIENT(D_UUID,D_CLIENTNAME, D_CLIENTID, D_CLIENTSECRET) values(#{uuid},#{clientName},#{clientId},#{clientSecret})")
  int createClient(final Client client);


  @Update("update SPB_AUTH_CLIENT set D_CLIENTNAME=#{clientName},D_CLIENTID=#{clientId},D_CLIENTSECRET=#{clientSecret} where D_UUID=#{uuid}")
  int updateClient(Client client);

  @Delete("delete from SPB_AUTH_CLIENT where D_UUID=#{uuid}")
  int deleteClient(String uuid);

  @Select("select * from SPB_AUTH_CLIENT where D_UUID=#{uuid}")
  @One
  @ResultMap("Client")
  Client findOne(String uuid);

  @Select("select * from SPB_AUTH_CLIENT")
  @Many
  @ResultMap("Client")
  List<Client> findAll();


  @Select("select * from SPB_AUTH_CLIENT where D_CLIENTID=#{clientId}")
  @One
  @ResultMap("Client")
  Client findByClientId(String clientId);


  @Select("select * from SPB_AUTH_CLIENT where D_CLIENTSECRET=#{clientSecret}")
  @One
  @ResultMap("Client")
  Client findByClientSecret(String clientSecret);
}
