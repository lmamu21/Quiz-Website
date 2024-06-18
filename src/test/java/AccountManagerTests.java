import Commons.AccountManager;
import Commons.User;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;


public class AccountManagerTests  {


    private AccountManager am;

    @BeforeEach
    public void setup() throws NoSuchAlgorithmException {
        am = new AccountManager("test_users");
        if(am==null) {
            System.out.println("am is NULL");
        }
    }

    @Test
    public void testRegisterUserSuccessfully() throws Exception {
        am.registerUser("testUser", "testPassword");
        User user = am.getUser("testUser");

        assertEquals("testUser", user.getUsername());
        assertNotNull(user.getHashedPassword());
        assertNotNull(user.getSalt());
    }

    @Test
    public void testRegisterUserWithExistingUsername() throws Exception {
//        assertThrows(Exception.class, () -> {
//            am.registerUser("testUser", "testPassword");
//            am.registerUser("testUser", "anotherPassword");
//        });
        assertTrue(am.registerUser("testUser", "testPassword"));
        assertFalse(am.registerUser("testUser", "anotherPassword"));
    }

    @Test
    public void testAuthenticateUserSuccessfully() throws Exception {
        am.registerUser("testUser1", "testPassword1");
        assertTrue(am.authenticateUser("testUser1", "testPassword1"));
    }

    @Test
    public void testAuthenticateUserWithWrongPassword() throws Exception {
        am.registerUser("testUser3", "testPassword3");
        assertFalse(am.authenticateUser("testUser3", "wrongPassword3"));
    }

    @Test
    public void testAuthenticateNonExistentUser() {
        assertThrows(Exception.class, () -> {
            am.authenticateUser("nonExistentUser", "testPassword");
        });
    }

    @Test
    public void testChangePasswordSuccessfully() throws Exception {
        am.registerUser("testUser4", "testPassword4");
        am.changePassword("testUser4", "newPassword4");

        assertTrue(am.authenticateUser("testUser4", "newPassword4"));
        assertFalse(am.authenticateUser("testUser4", "testPassword4"));
    }

    @Test
    public void testChangePasswordForNonExistentUser() {
        assertThrows(Exception.class, () -> {
            am.changePassword("nonExistentUser", "newPassword");
        });
    }

    @Test
    public void testGetUserSuccessfully() throws Exception {
        am.registerUser("testUser5", "testPassword5");
        User user = am.getUser("testUser5");

        assertNotNull(user);
        assertEquals("testUser5", user.getUsername());
    }

    @Test
    public void testGetNonExistentUser() {
        assertThrows(Exception.class, () -> {
            am.getUser("nonExistentUser");
        });
    }
}



