package com.udacity.jwdnd.course1.cloudstorage.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;

import lombok.Data;

@Data
public class Credential {
    private static final Logger LOG = LogManager.getLogger(Credential.class);
    private Integer credentialid;
    private String url;
    private String username;
    private String key;
    private String password;
    private String decryptedPassword;
    private Integer userid;

    /**
     * @param credentialId
     * @param url
     * @param username
     * @param key
     * @param password
     * @param userId
     */
    public Credential(Integer credentialid, String url, String username, String key, String password, Integer userid) {
        this.credentialid = credentialid;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.userid = userid;
    }

    public String getDecryptedPassword() {
        try {
            if (this.password != null && this.key != null) {
                EncryptionService encryptionService = new EncryptionService();
                this.decryptedPassword = encryptionService.decryptValue(this.password, this.key);
            }
        } catch (Exception e) {
            LOG.error(e);
        }

        return this.decryptedPassword;
    }
}
