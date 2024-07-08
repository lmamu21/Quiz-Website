package Commons;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class AccountManager {
    private byte[] salt;

    private AccountManagerDAO accountManagerDAO;

    public AccountManager(AccountManagerDAO accountManagerDAO){
       this.accountManagerDAO=accountManagerDAO;
    }

    public boolean registerUser(String username, String password) throws NoSuchAlgorithmException {
        this.salt = generateSalt();
        String hashedPassword = passwordToHash(password,salt);
        String saltString = hexToString(salt);
        return  accountManagerDAO.addUser(username, hashedPassword, hexToString(salt));
    }


    public boolean authenticateUser(String username,String password) throws Exception {
        byte[] salt = hexToArray(accountManagerDAO.getSalt(username));
        String hashedPassword = accountManagerDAO.getPasswordHash(username);
        return passwordToHash(password, salt).equals(hashedPassword);
    }

    public void changePassword(String username, String newPassword) throws Exception {
        // TODO: user should enter current password and new password and it should be validated
        byte[] salt = generateSalt();
        String newPasswordHash = passwordToHash(newPassword, salt);
        accountManagerDAO.changePassword(username, newPassword, hexToString(salt));
    }

    public String getID(String username) throws Exception{
        return  accountManagerDAO.getID(username);
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

    private byte[] hexToArray(String hex) {
        byte[] result = new byte[hex.length()/2];
        for (int i=0; i<hex.length(); i+=2) {
            result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
        }
        return result;
    }


    public String getUsername(int user_id) {
        return accountManagerDAO.getUsername(user_id);
    }
}
