/*
    Inventory and User Mangement Web App
    This program creates a web app to allow users to manipulate data in inventory
    and user list.
    May 3, 2021
 */
package mags;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This is the UserDAO and it implements the interface DAO for User.
 * @author leadermags
 * @param <User> 
 */
public class FileUserDao<User> implements DataAccessObject<User> {
    
    private List<User> users = new ArrayList<>();

    @Override
    public List<User> readAll() {
        return this.users;
    }

    @Override
    public User read(Object id) {
        User user = null;
        for(User u: users){
            if(u.equals((User) id)){
                return (User) id;
            }
        }
        return user;
    }

    @Override
    public void create(User e) {
        users.add(e);
    }

    @Override
    public void update(User e) {
        User user = null;
        for(User u: users){
            if(u.equals(e)){
                user = u;
            } else {
            }
        }
        if(user != null){
            users.remove(user);
        }
        users.add(e);
    }

    @Override
    public void delete(Object id) {
        for(User u: users){
            if(u.equals((User) id)){
                users.remove((User) id);
                break;
            }
        }
    }
    
}
