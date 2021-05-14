/*
    Inventory and User Mangement Web App
    This program creates a web app to allow users to manipulate data in inventory
    and user list.
    May 3, 2021
 */
package mags;

/**
 *
 * This is the DAOFactory for productDAO and userDAO.
 * @author leadermags
 */
public class DataAccessObjectFactory {

    public static DataAccessObject<Product> getProductDao() throws DataAccessException{
        return new DatabaseProductDao();
    }
    
    public static DataAccessObject<User> getUserDao() throws DataAccessException{
        return new DatabaseUserDao();
    }
    
}
