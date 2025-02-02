package com.passwordmanager;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtils {
    private static final int ITERATIONS = 100000; // High iteration count for security
    private static final int KEY_LENGTH = 256; // Key length in bits
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
    
    
    // generate a random salt
    public static String generateSalt() {
    	byte[] salt = new byte[16];
    	new SecureRandom().nextBytes(salt);
    	return Base64.getEncoder().encodeToString(salt);
    	
    }
    
    // hash password using PBKDF2
    public static String hashPassword(String password, String salt) {
        try {
            byte[] saltBytes = Base64.getDecoder().decode(salt);
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error while hashing password", e);
        }
    }
    
    // password validation
    public static boolean validatePassword(String inputPassword, String storedHash, String storedSalt) {
        String hashedPassword = hashPassword(inputPassword, storedSalt);
        return hashedPassword.equals(storedHash);
    }
    
}
