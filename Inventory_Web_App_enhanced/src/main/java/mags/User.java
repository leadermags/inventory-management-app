/*
    Inventory and User Mangement Web App
    This program creates a web app to allow users to manipulate data in inventory
    and user list.
    May 3, 2021
 */
package mags;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author leadermags
 */
public class User implements Serializable {

    // Role types
    public static final String ADMINISTRATOR = "ADMINISTRATOR";
    public static final String INVENTORY_MANAGER = "INVMAN";
    public static final String USER_MANAGER = "USRMAN";
    public static final String INVENTORY_VIEWER = "VIEWER";

    // User info
    private String username;
    private String password;
    private String salt;
    private Set<String> roles = new HashSet<>();

    /**
     * empty constructor.
     */
    User() {
        
    }

    /**
     * 
     * constructor to initialize User with following parameters.
     * @param username
     * @param password
     * @param salt
     * @param rolesSet 
     */
    User(String username, String password, String salt, Set<String> rolesSet) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.roles = rolesSet;
    }
    
    /**
     * Return random salt for password.
     * @return 
     */
    public String getSalt(){
        return this.salt;
    }
    
    /**
     * Set random salt for password.
     * @param salt 
     */
    public void setSalt(String salt){
        this.salt = salt;
    }

    /**
     * Return username.
     * @return 
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Set username.
     * @param username 
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Return password.
     * @return 
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Set password.
     * @param password 
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Return roles.
     * @return 
     */
    public Set<String> getRoles() {
        return this.roles;
    }

    /**
     * Set roles.
     * @param roles 
     */
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    /**
     * Return boolean if user has role.
     * @param role
     * @return 
     */
    public boolean hasRole(String role) {
        return roles.contains(ADMINISTRATOR) || roles.contains(role);
    }
}
