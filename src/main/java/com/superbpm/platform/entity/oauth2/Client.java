package com.superbpm.platform.entity.oauth2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 客户端
 */
@Entity
@Table(name = "SPB_AUTH_CLIENT")
public class Client implements Serializable {

  @Id
  @Column(name = "D_UUID", nullable = false, length = 64)
  protected String uuid;

  @Column(name = "D_CLIENTNAME", nullable = false, length = 100)
  private String clientName;

  @Column(name = "D_CLIENTID", nullable = false, length = 200)
  private String clientId;

  @Column(name = "D_CLIENTSECRET", nullable = false, length = 200)
  private String clientSecret;

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getClientName() {
    return clientName;
  }

  public void setClientName(String clientName) {
    this.clientName = clientName;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Client client = (Client) o;

    if (uuid != null ? !uuid.equals(client.uuid) : client.uuid != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return uuid != null ? uuid.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "Client{" +
            "id=" + uuid +
            ", clientName='" + clientName + '\'' +
            ", clientId='" + clientId + '\'' +
            ", clientSecret='" + clientSecret + '\'' +
            '}';
  }
}
