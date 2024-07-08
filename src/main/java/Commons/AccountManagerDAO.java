package Commons;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountManagerDAO {
    private DataSource pool;
    private String databaseName;
    public AccountManagerDAO(DataSource pool, String databaseName) {
        this.pool = pool;
        this.databaseName = databaseName;
    }

    public boolean addUser(String username, String password_hash, String salt){
        Connection con = null;

        try {
            con = pool.getConnection();
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //stmt.executeQuery("USE " + databaseName);
            int result = 0;
            StringBuilder builder = new StringBuilder();
            builder.append("INSERT INTO users");
            builder.append(" (username, password_hashed, salt) ");
            builder.append(" VALUES ");
            builder.append(String.format("(\'%s\', \'%s\', \'%s\')", username, password_hash, salt));
            String query = builder.toString();
            System.out.println(query);
            result = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            System.out.println(query);
            System.out.println(result);
            if (result == 0) {
                throw new IllegalArgumentException();
            }
            stmt.close();
            con.close();
            System.out.println(result);
            return result == 1;
        }catch (SQLException e) {
            e.getStackTrace();
        } finally {
            if (con != null) try {
                // Returns the connection to the pool.
                con.close();
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    public String getPasswordHash(String username) {
        Connection con = null;
        String result = "";
        try {
            con = pool.getConnection();
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = String.format("SELECT password_hashed FROM users WHERE username = \'%s\'", username);
            try {
                ResultSet resultSet = stmt.executeQuery(query);

                if (resultSet.next()) {
                    // means that there was entry with provided username
                    result = resultSet.getString(1);
                } else {
                    throw new IllegalArgumentException();
                }
                resultSet.close();
            } catch (IllegalArgumentException e) {
                System.out.println("ERROR: Could not find entry with username \'" + username + "\'");
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("ERROR: SQL connection error");
                e.printStackTrace();
            }
            stmt.close();
            con.close();

        }catch (SQLException e) {
            e.getStackTrace();
        } finally {
            if (con != null) try {
                // Returns the connection to the pool.
                con.close();
            } catch (Exception ignored) {
            }
        }
        return result;
    }

    public boolean changePassword(String username, String newPassword, String salt) {

        Connection con = null;

        try {
            con = pool.getConnection();
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = String.format("UPDATE users SET password_hashed = \'%s\', salt = \'%s\' WHERE username = \'%s\'", newPassword, salt, username);
            try {
                int result = stmt.executeUpdate(query);
                if (result == 0) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                System.out.println("ERROR: Could not change password of user \'" + username + "\' to the database");
                e.printStackTrace();
                return false;
            } catch (SQLException e) {
                System.out.println("ERROR: SQL connection error");
                e.printStackTrace();
                return false;
            }
            return true;
        }catch (SQLException e) {
                e.getStackTrace();
            } finally {
                if (con != null) try {
                    // Returns the connection to the pool.
                    con.close();
                } catch (Exception ignored) {
                }
            }
        return false;
    }

    public String getSalt(String username) {
        Connection con = null;
        String result = "";
        try {
            con = pool.getConnection();
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = String.format("SELECT salt FROM users WHERE username = \'%s\'", username);

            try {
                ResultSet resultSet = stmt.executeQuery(query);

                if (resultSet.next()) {
                    // means that there was entry with provided username
                    result = resultSet.getString(1);
                }else {
                    throw new IllegalArgumentException();
                }
                resultSet.close();
            }catch(IllegalArgumentException e) {
                e.printStackTrace();
                throw new IllegalArgumentException();
            }catch(SQLException e){
                System.out.println("ERROR: SQL connection error");
                e.printStackTrace();
            }
            con.close();
            stmt.close();

        }catch (SQLException e) {
            e.getStackTrace();
        } finally {
            if (con != null) try {
                // Returns the connection to the pool.
                con.close();
            } catch (Exception ignored) {
            }
        }
        return result;
    }


}
