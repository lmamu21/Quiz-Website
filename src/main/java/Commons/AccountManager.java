package Commons;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class AccountManager {


    private byte[] salt;

    private Map<String, User> userDatabase = new HashMap<>();

    public void registerUser(String username, String password) throws Exception {
        if(userDatabase.containsKey(username)){
            throw new Exception("Username already exists.");
        }
        this.salt = generateSalt();
        String hashedPassword = passwordToHash(password,salt);
        User user = new User(username,hashedPassword);
        user.setSalt(salt);
        userDatabase.put(username,user);
    }


    public boolean authenticateUser(String username,String password) throws Exception {
        User user = userDatabase.get(username);
        if(user == null){
            throw new Exception("User not found.");
        }
        return passwordToHash(password,user.getSalt()) == user.getHashedPassword();
    }

    public void changePassword(String username, String newPassword) throws Exception {
        User user = userDatabase.get(username);

        if (user == null) {
            throw new Exception("User not found.");
        }

        user.setPassword(passwordToHash(newPassword,user.getSalt()));
    }

    public User getUser(String username) throws Exception {
        User user = userDatabase.get(username);

        if (user == null) {
            throw new Exception("User not found.");
        }

        return user;
    }


    private static String passwordToHash(String password,byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(salt);
        md.update(password.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        return hexToString(digest);

    }

    private static String hexToString(byte[] bytes) {
        StringBuffer buff = new StringBuffer();
        for (int i=0; i<bytes.length; i++) {
            int val = bytes[i];
            val = val & 0xff;  // remove higher bits, sign
            if (val<16) buff.append('0'); // leading 0
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // 16 bytes salt
        random.nextBytes(salt);
        return salt;
    }

}
