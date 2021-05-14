/*
    Inventory and User Mangement Web App
    This program creates a web app to allow users to manipulate data in inventory
    and user list.
    May 3, 2021
 */
package mags;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Set;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * This class helps manage the users.
 *
 * @author leadermags
 */
public class UserManager {

    private final DataAccessObject<User> userDao;
   
    
    public UserManager() throws DataAccessException {
        this.userDao = DataAccessObjectFactory.getUserDao();
    }

    /**
     * Generates and returns a random salt.
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    /**
     * Convert raw password to secure salted hash of password.
     *
     * @param rawPassword
     * @param salt
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static String passwordToHash(String rawPassword, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {

        // Hash raw password using PBKDF2
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(rawPassword.toCharArray(), salt.getBytes(), 1000, 64 * 8);
        byte[] hash = skf.generateSecret(spec).getEncoded();

        // Convert hash into string
        return byteToString(hash);
    }

    /**
     * Convert byte array to string representation. Used to return string
     * representation of a hash and salt.
     *
     * @param array
     * @return
     */
    private static String byteToString(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    /**
     * Takes in username to check if user is in list.
     *
     * @param username
     * @return
     * @throws DataAccessException
     */
    public User getUser(String username) throws DataAccessException {
        return userDao.read(username);
    }

    /**
     * Return list of users.
     *
     * @return
     * @throws DataAccessException
     */
    public List<User> getUsers() throws DataAccessException {
        return userDao.readAll();
    }

    /**
     * Create User with random salt value set and password replaced with secure
     * hash set.
     *
     * @param u
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws DataAccessException
     */
    public void addUser(User u) throws NoSuchAlgorithmException, InvalidKeySpecException, DataAccessException {
        // Create random salt
        byte[] salt = getSalt();
        // Convert salt to string
        String saltString = byteToString(salt);
        // Convert plain text password to secure hash
        String hash = passwordToHash(u.getPassword(), saltString);

        u.setPassword(hash);
        u.setSalt(saltString);
        userDao.create(u);
    }

    /**
     * Remove user in list
     *
     * @param username
     * @throws DataAccessException
     */
    public void removeUser(String username) throws DataAccessException {
        userDao.delete(username);
    }

    /**
     * Update User password with new random salt and replace password with new
     * secure hash.
     *
     * @param username
     * @param password
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws DataAccessException
     */
    public void changePassword(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException, DataAccessException {
        // Create new object
        User u = userDao.read(username);

        // Create random salt
        byte[] salt = getSalt();
        // Convert salt to string
        String saltString = byteToString(salt);
        // Convert plain text password to secure hash
        String hash = passwordToHash(password, saltString);

        u.setSalt(saltString);
        u.setPassword(hash);
        userDao.update(u);
    }

    /**
     * Validate User login info with saved salt and hashed password.
     *
     * @param username
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws DataAccessException
     */
    public boolean checkLogin(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException, DataAccessException {
        List<User> users = userDao.readAll();

        for (User u : users) {
            if (u.getUsername().equals(username)) {
                String salt = u.getSalt();
                String hash = passwordToHash(password, salt);
                return u.getPassword().equals(hash);
            }
        }
        return false;
    }

    /**
     * Update roles of user
     *
     * @param username
     * @param roles
     * @throws DataAccessException
     */
    public void updateRoles(String username, Set<String> roles) throws DataAccessException {
        // Create new object
        User u = userDao.read(username);

        u.setRoles(roles);

        userDao.update(u);

    }

    /**
     * Check if user list in table has an Administrator or User Manager role.
     * @return 
     * @throws DataAccessException
     */
    public boolean hasUserMan() throws DataAccessException {
        List<User> users = userDao.readAll();

        for (User u : users) {
            if (u.hasRole("ADMINISTRATOR") || u.hasRole("USER_MANAGER")) {
                return true;
            }
        }

        return false;
    }
}
