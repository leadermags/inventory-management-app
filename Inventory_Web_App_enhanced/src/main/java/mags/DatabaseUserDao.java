/*
    Inventory and User Mangement Web App
    This program creates a web app to allow users to manipulate data in inventory
    and user list.
    May 3, 2021
 */
package mags;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * This class connects to database to manipulate user data.
 *
 * @author leadermags
 */
public class DatabaseUserDao implements DataAccessObject<User> {

    // database URL
    private static final String CONNECT_URL = "jdbc:derby://localhost:1527/store;create=true";
    // SQL statements
    private static final String CREATE_TABLE = "CREATE TABLE USER_T (USERNAME VARCHAR(25), PASSWORD VARCHAR(500), SALT VARCHAR(500), ROLES VARCHAR(5000))";
    private static final String READ_ALL = "SELECT USERNAME, PASSWORD, SALT, ROLES FROM USER_T";
    private static final String READ = "SELECT USERNAME, PASSWORD, SALT, ROLES FROM USER_T WHERE USERNAME = ?";
    private static final String INSERT = "INSERT INTO USER_T (USERNAME, PASSWORD, SALT, ROLES) VALUES (?,?,?,?)";
    private static final String UPDATE = "UPDATE USER_T SET PASSWORD=?, SALT=?, ROLES=? WHERE USERNAME = ?";
    private static final String DELETE = "DELETE FROM USER_T WHERE USERNAME = ?";

    /**
     * Check to create table USER if not exists.
     *
     * @throws DataAccessException
     */
    public DatabaseUserDao() throws DataAccessException {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection(CONNECT_URL);
            boolean exists = conn.getMetaData().getTables(null, null, "USER_T", null).next();
            if (!exists) {
                PreparedStatement create = conn.prepareStatement(CREATE_TABLE);
                create.execute();
            }
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseUserDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseUserDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException("Could not connect to database. "
                    + "There was a database error.", ex);
        }

    }

    /**
     * Read all of users in inventory.
     *
     * @return
     * @throws DataAccessException
     */
    @Override
    public List<User> readAll() throws DataAccessException {

        List<User> l = new ArrayList<>();

        try ( Connection conn = DriverManager.getConnection(CONNECT_URL)) {
            PreparedStatement statement = conn.prepareStatement(READ_ALL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String username = rs.getString("USERNAME");
                String password = rs.getString("PASSWORD");
                String salt = rs.getString("SALT");
                String roles = rs.getString("ROLES");
                String[] rolesArr = roles.split(", ");
                Set<String> rolesSet = new HashSet<>(Arrays.asList(rolesArr));
                User u = new User(username, password, salt, rolesSet);
                l.add(u);
            }
        } catch (NullPointerException ex) {
            Logger.getLogger(DatabaseUserDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseUserDao.class.getName()).log(Level.SEVERE, null, ex);
            DataAccessException dax = new DataAccessException("Could not read data from User table. "
                    + "There was a database error.", ex);

            throw dax;

        }
        return l;
    }

    /**
     * Read user with matching ID (username).
     *
     * @param id
     * @return
     * @throws DataAccessException
     */
    @Override
    public User read(Object id) throws DataAccessException {
        User u = null;

        try ( Connection conn = DriverManager.getConnection(CONNECT_URL)) {
            PreparedStatement statement = conn.prepareStatement(READ);
            statement.setString(1, id.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                String username = rs.getString("USERNAME");
                String password = rs.getString("PASSWORD");
                String salt = rs.getString("SALT");
                String roles = rs.getString("ROLES");
                String[] rolesArr = roles.split(", ");
                Set<String> rolesSet = new HashSet<>(Arrays.asList(rolesArr));
                u = new User(username, password, salt, rolesSet);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseUserDao.class.getName()).log(Level.SEVERE, null, ex);
            DataAccessException dax = new DataAccessException("Could not read data from User table. "
                    + "There was a database error.", ex);

            throw dax;

        }

        return u;
    }

    /**
     * Create user in database.
     *
     * @param e
     * @throws DataAccessException
     */
    @Override
    public void create(User e) throws DataAccessException {

        try ( Connection conn = DriverManager.getConnection(CONNECT_URL)) {
            PreparedStatement statement = conn.prepareStatement(INSERT);
            statement.setString(1, e.getUsername());
            statement.setString(2, e.getPassword());
            // TODO - SALT
            statement.setString(3, e.getSalt());
            statement.setString(4, e.getRoles().toString().replace("[", "").replace("]", ""));
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseUserDao.class.getName()).log(Level.SEVERE, null, ex);
            DataAccessException dax = new DataAccessException("Could not create user. "
                    + "There was a database error.", ex);

            throw dax;

        }
    }

    /**
     * Update user with matching id (username) in database.
     *
     * @param e
     * @throws DataAccessException
     */
    @Override
    public void update(User e) throws DataAccessException {
        try ( Connection conn = DriverManager.getConnection(CONNECT_URL)) {
            PreparedStatement statement = conn.prepareStatement(UPDATE);

            statement.setString(1, e.getPassword());
            // TODO - salt
            statement.setString(2, e.getSalt());
            statement.setString(3, e.getRoles().toString().replace("[", "").replace("]", ""));
            statement.setString(4, e.getUsername());
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseUserDao.class.getName()).log(Level.SEVERE, null, ex);
            DataAccessException dax = new DataAccessException("Could not update this user. "
                    + "There was a database error.", ex);

            throw dax;

        }
    }

    /**
     * Delete user with matching id (username) in database.
     *
     * @param id
     * @throws DataAccessException
     */
    @Override
    public void delete(Object id) throws DataAccessException {
        try ( Connection conn = DriverManager.getConnection(CONNECT_URL)) {
            PreparedStatement statement = conn.prepareStatement(DELETE);
            statement.setString(1, id.toString());
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseUserDao.class.getName()).log(Level.SEVERE, null, ex);
            DataAccessException dax = new DataAccessException("Could not delete this user. "
                    + "There was a database error.", ex);

            throw dax;

        }
    }

}
