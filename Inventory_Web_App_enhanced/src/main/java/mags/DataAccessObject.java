/*
    Inventory and User Mangement Web App
    This program creates a web app to allow users to manipulate data in inventory
    and user list.
    May 3, 2021
 */
package mags;

import java.util.List;

/**
 *
 * This is the DAO interface to outline for ProductDAO and UserDAO.
 * @author leadermags
 * @param <E>
 */
public interface DataAccessObject<E> {
    
    public List<E> readAll() throws DataAccessException;
    public E read(Object id) throws DataAccessException;
    public void create(E e) throws DataAccessException;
    public void update(E e) throws DataAccessException;
    public void delete(Object id) throws DataAccessException;
    
}
