package service.impl;

import exceptions.PasswordEncodeException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoderService {
    private static final Logger LOGGER = LogManager.getLogger(PasswordEncoderService.class);

    public String encode(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.warn("Password encode error");
            throw new PasswordEncodeException("Password encode error");
        }
    }
}
