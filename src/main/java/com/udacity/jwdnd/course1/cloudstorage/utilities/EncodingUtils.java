package com.udacity.jwdnd.course1.cloudstorage.utilities;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.Base64;

import javax.crypto.KeyGenerator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EncodingUtils {

    private static final Logger LOG = LogManager.getLogger(EncodingUtils.class);

    private EncodingUtils() {
        throw new AssertionError();
    }

    public static String createEncodedSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String createEncodedKey() {
        try {
            return Base64.getEncoder().encodeToString(KeyGenerator.getInstance("AES").generateKey().getEncoded());
        } catch (NoSuchAlgorithmException nsae) {
            LOG.error(MessageFormat.format("Error Creating Encoded Key: ", nsae));
            return null;
        }
    }

}
