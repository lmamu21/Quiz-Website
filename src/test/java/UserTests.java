import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Commons.User;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.*;
public class UserTests {
    private User user;

    @BeforeEach
    public void setup() throws NoSuchAlgorithmException {
        user = new User("testUsername","testPassword");
    }

    @Test
    public void testUserCreation() throws NoSuchAlgorithmException {
        assertEquals(user.getUsername(),"testUsername");
        assertEquals("testPassword", user.getHashedPassword());
    }

    @Test
    public void testSetUsername(){
        user.setUsername("newUsername");
        assertEquals(user.getUsername(),"newUsername");
    }

    @Test
    public void testSetPassword() throws NoSuchAlgorithmException {
        user.setPassword("newPassword");
        assertEquals("newPassword",user.getHashedPassword());

    }

    @Test
    public void testSetSalt(){
        byte[] salt = generateSalt();
        user.setSalt(salt);
        assertEquals(salt,user.getSalt());
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // 16 bytes salt
        random.nextBytes(salt);
        return salt;
    }








}
