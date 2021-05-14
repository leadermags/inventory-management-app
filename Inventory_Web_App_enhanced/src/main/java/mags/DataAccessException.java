/*
    Inventory and User Mangement Web App
    This program creates a web app to allow users to manipulate data in inventory
    and user list.
    May 3, 2021
 */
package mags;

/**
 * This class creates Data Access Exceptions.
 * 
 * @author leadermags
 */
public class DataAccessException extends Exception {
    
    /**
     * Default constructor for Exception.
     */
    public DataAccessException(){
        super();
    }
    
    /**
     * Constructor with message for Exception.
     * @param message 
     */
    public DataAccessException(String message){
        super(message);
    }
    
    /**
     * Constructor with cause thrown for Exception.
     * @param cause 
     */
    public DataAccessException(Throwable cause){
        super(cause);
    }
    
    /**
     * Constructor with message and cause thrown for Exception.
     * @param message
     * @param cause 
     */
    public DataAccessException(String message, Throwable cause){
        super(message, cause);
    }
    
}
