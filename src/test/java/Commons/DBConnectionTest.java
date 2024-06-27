//package Commons;
//
//import junit.framework.TestCase;
//
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.Statement;
//
//public class DBConnectionTest extends TestCase {
//    Connection conn = null;
//    Statement stmt = null;
//    DBConnection dbConn = null;
//    /*
//      connects to database itself, without DBConnection class to clean up, so it runs tests on clean database
//      and then creates DBConnection object that will be used in tests
//     */
//    @Override
//    public void setUp() throws Exception {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "admin");
//            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//            stmt.execute(String.format("USE quiz_test"));
//        } catch (Exception e) {
//            System.out.println("ERROR: Could not connect to database quiz_test");
//            e.printStackTrace();
//        }
//        try {
//            String sql = new String(Files.readAllBytes(Paths.get("src/main/resources/user.sql")));
//            String[] statements = sql.split(";");
//            for (String statement : statements) {
//                stmt.execute(statement.trim());
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        dbConn = new DBConnection("quiz_test");
//    }
//
//    /*
//    !!!!DISCLAIMER!!!!!
//    tests are not run in any order - it is managed by junit.
//    Therefore test implementation should take consider generality
//     */
//
//    public void testSimpleAdd(){
//        dbConn.addUser("Free", "University", hexToString(salt));
//        dbConn.addUser("Agricultural", "University", hexToString(salt));
//        dbConn.addUser("Bendukidze", "Campus", hexToString(salt));
//
//        assertEquals("University", dbConn.getPasswordHash("Free"));
//        assertEquals("University", dbConn.getPasswordHash("Agricultural"));
//        assertEquals("Campus", dbConn.getPasswordHash("Bendukidze"));
//
//    }
//
//    public void testComplexAdd(){
//        dbConn.addUser("Programming", "Paradigms", hexToString(salt));
//        dbConn.addUser("Data", "Bases", hexToString(salt));
//        dbConn.addUser("Discrete", "Mathematics", hexToString(salt));
//        boolean result = dbConn.addUser("Programming", "Abstractions", hexToString(salt));
//
//        // should not be added
//        assertFalse(result);
//
//        // earlier user should remain its password
//        assertEquals("Paradigms", dbConn.getPasswordHash("Programming"));
//
//        assertEquals("Bases", dbConn.getPasswordHash("Data"));
//        assertEquals("Mathematics", dbConn.getPasswordHash("Discrete"));
//    }
//
//    public void testChangePassword(){
//        dbConn.addUser("Vanilla", "Ice-cream", hexToString(salt));
//        dbConn.addUser("Chocolate", "Cake", hexToString(salt));
//        dbConn.addUser("Apple", "Pie", hexToString(salt));
//
//        dbConn.changePassword("Chocolate", "Ice-cream", hexToString(salt));
//        dbConn.changePassword("Apple", "Jam", hexToString(salt));
//
//        assertEquals("Ice-cream", dbConn.getPasswordHash("Chocolate"));
//        assertEquals("Jam", dbConn.getPasswordHash("Apple"));
//        assertEquals("Ice-cream", dbConn.getPasswordHash("Vanilla"));
//    }
//
//    public void testGetCount(){
//        DBConnection localConn = new DBConnection("quiz_test");
//        assertEquals(0, localConn.getCount());
//
//        localConn.addUser("Free", "University", hexToString(salt));
//        localConn.addUser("Agricultural", "University", hexToString(salt));
//        localConn.addUser("Apple", "Pie", hexToString(salt));
//
//        assertEquals(3, localConn.getCount());
//
//        localConn.addUser("Free", "Paradigms", hexToString(salt));
//
//        assertEquals(3, localConn.getCount());
//
//        localConn.changePassword("Apple", "Ice-cream", hexToString(salt));
//        assertEquals(3, localConn.getCount());
//
//        localConn.addUser("Programming", "Abstractions", hexToString(salt));
//        assertEquals(4, localConn.getCount());
//    }
//
//}
