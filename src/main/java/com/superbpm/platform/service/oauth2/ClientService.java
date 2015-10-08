package com.superbpm.platform.service.oauth2;

import com.superbpm.platform.dao.oauth2.ClientDao;
import com.superbpm.platform.entity.oauth2.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class ClientService {

  @Autowired
  private ClientDao clientDao;

  public Client createClient(Client client) {
    client.setUuid(UUID.randomUUID().toString());
    client.setClientId(UUID.randomUUID().toString());
    client.setClientSecret(UUID.randomUUID().toString());
    clientDao.createClient(client);
    return client;
  }

  public int updateClient(Client client) {
    return clientDao.updateClient(client);
  }

  public void deleteClient(String uuid) {
    clientDao.deleteClient(uuid);
  }

  public Client findOne(String uuid) {
    return clientDao.findOne(uuid);
  }

  public List<Client> findAll() {
    return clientDao.findAll();
  }

  public Client findByClientId(String clientId) {
    return clientDao.findByClientId(clientId);
  }

  public Client findByClientSecret(String clientSecret) {
    return clientDao.findByClientSecret(clientSecret);
  }
}
