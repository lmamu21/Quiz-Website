package Commons;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class User {

    private String username;

    private String hashedPassword;

    private byte[] salt;

    public User(String username, String password) throws NoSuchAlgorithmException {
        this.username = username;
        this.hashedPassword =  password;

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException {
        this.hashedPassword = password;
    }


    public byte[] getSalt(){
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}
