package com.superbpm.platform.entity.tenant;

import com.superbpm.platform.PlatformConstants;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * platform
 *
 * @author rock
 */
public class App implements Serializable {

    @Id
    @Column(name = "D_UUID", nullable = false, length = 64)
    protected String uuid;

    private String bucket;
    private String name;
    private Date createTime;
    private String user;
    private int dbType;
    private String dbDriverClass;
    private String dbURL;
    private String dbUsername;
    private String dbPassword;
    private String secretKey;
    private int status;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getDbType() {
        return dbType;
    }

    public void setDbType(int dbType) {
        this.dbType = dbType;
    }

    public String getDbDriverClass() {
        return dbDriverClass;
    }

    public void setDbDriverClass(String dbDriverClass) {
        this.dbDriverClass = dbDriverClass;
    }

    public String getDbURL() {
        return dbURL;
    }

    public void setDbURL(String dbURL) {
        this.dbURL = dbURL;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDatabase() {
        String db = null;
        if (StringUtils.isNotBlank(dbURL)) {
            int end = dbURL.lastIndexOf("?");
            int start = dbURL.lastIndexOf("/") + 1;
            if (end == -1) {
                end = dbURL.length();
            }
            db = dbURL.substring(start, end);
        } else {
            if (PlatformConstants.TENANT_APP_DB_TYPE_MYSQL != getDbType()) {
                db = this.bucket;
            }
        }
        return db;
    }
}
