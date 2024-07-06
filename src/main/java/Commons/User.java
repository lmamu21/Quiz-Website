package Commons;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class User {
    private int userId;

    private String username;

    public User(int userId,String username) {
        this.userId = userId;
        this.username = username;

    }

    public String getUsername() {
        return username;
    }

    public int getUserId(){
        return userId;
    }

}
