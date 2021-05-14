/*
    Inventory and User Mangement Web App
    This program creates a web app to allow users to manipulate data in inventory
    and user list.
    May 3, 2021
 */
package mags;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * This class connects to database to manipulate inventory data.
 *
 * @author leadermags
 */
public class DatabaseProductDao implements DataAccessObject<Product> {

    // database URL
    private static final String CONNECT_URL = "jdbc:derby://localhost:1527/store;create=true";
    // SQL statements
    private static final String CREATE_TABLE = "CREATE TABLE PRODUCT (UPC VARCHAR(25), DESCRIPTION VARCHAR(5000), PRICE DECIMAL(10,2), STOCK INTEGER, PRIMARY KEY (UPC))";
    private static final String READ_ALL = "SELECT UPC, DESCRIPTION, PRICE, STOCK FROM PRODUCT";
    private static final String READ = "SELECT UPC, DESCRIPTION, PRICE, STOCK FROM PRODUCT WHERE UPC = ?";
    private static final String INSERT = "INSERT INTO PRODUCT (UPC, DESCRIPTION, PRICE, STOCK) VALUES (?,?,?,?)";
    private static final String UPDATE = "UPDATE PRODUCT SET DESCRIPTION=?, PRICE=?, STOCK=? WHERE UPC = ?";
    private static final String DELETE = "DELETE FROM PRODUCT WHERE UPC = ?";

    /**
     * Check to create table PRODUCT if not exists.
     *
     * @throws DataAccessException
     */
    public DatabaseProductDao() throws DataAccessException {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection(CONNECT_URL);
            boolean exists = conn.getMetaData().getTables(null, null, "PRODUCT", null).next();
            if (!exists) {
                PreparedStatement create = conn.prepareStatement(CREATE_TABLE);
                create.execute();
            }
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseProductDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseUserDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException("Could not connect to database. "
                    + "There was a database error.", ex);
        }

    }

    /**
     * Read all of products in inventory.
     *
     * @return
     * @throws DataAccessException
     */
    @Override
    public List<Product> readAll() throws DataAccessException {

        List<Product> l = new ArrayList<>();

        try ( Connection conn = DriverManager.getConnection(CONNECT_URL)) {
            PreparedStatement statement = conn.prepareStatement(READ_ALL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String upc = rs.getString("UPC");
                String desc = rs.getString("DESCRIPTION");
                BigDecimal price = rs.getBigDecimal("PRICE");
                int stock = rs.getInt("STOCK");
                Product p = new Product(upc, desc, price, stock);
                l.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseUserDao.class.getName()).log(Level.SEVERE, null, ex);
            DataAccessException dax = new DataAccessException("Could not read data from Product table. "
                    + "There was a database error.", ex);

            throw dax;

        }
        return l;
    }

    /**
     * Read product with matching ID (UPC).
     *
     * @param id
     * @return
     * @throws DataAccessException
     */
    @Override
    public Product read(Object id) throws DataAccessException {
        Product p = null;
        try ( Connection conn = DriverManager.getConnection(CONNECT_URL)) {
            PreparedStatement statement = conn.prepareStatement(READ);
            statement.setString(1, id.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String upc = rs.getString("UPC");
                String desc = rs.getString("DESCRIPTION");
                BigDecimal price = rs.getBigDecimal("PRICE");
                int stock = rs.getInt("STOCK");
                p = new Product(upc, desc, price, stock);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseUserDao.class.getName()).log(Level.SEVERE, null, ex);
            DataAccessException dax = new DataAccessException("Could not read data from Product table. "
                    + "There was a database error.", ex);

            throw dax;

        }
        return p;
    }

    /**
     * Create product in database.
     *
     * @param e
     * @throws DataAccessException
     */
    @Override
    public void create(Product e) throws DataAccessException {

        try ( Connection conn = DriverManager.getConnection(CONNECT_URL)) {
            PreparedStatement statement = conn.prepareStatement(INSERT);
            statement.setString(1, e.getUpc());
            statement.setString(2, e.getDescription());
            statement.setBigDecimal(3, e.getPrice());
            statement.setInt(4, e.getStock());
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseProductDao.class.getName()).log(Level.SEVERE, null, ex);
            DataAccessException dax = new DataAccessException("Could not create product. "
                    + "There was a database error.", ex);

            throw dax;
        }

    }

    /**
     * Update product with matching id (UPC) in database.
     *
     * @param e
     * @throws DataAccessException
     */
    @Override
    public void update(Product e) throws DataAccessException {

        try ( Connection conn = DriverManager.getConnection(CONNECT_URL)) {
            PreparedStatement statement = conn.prepareStatement(UPDATE);
            statement.setString(1, e.getDescription());
            statement.setBigDecimal(2, e.getPrice());
            statement.setInt(3, e.getStock());
            statement.setString(4, e.getUpc());
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseProductDao.class.getName()).log(Level.SEVERE, null, ex);
            DataAccessException dax = new DataAccessException("Could not update this product. "
                    + "There was a database error.", ex);

            throw dax;
        }

    }

    /**
     * Delete product with matching id (UPC) in database.
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
            Logger.getLogger(DatabaseProductDao.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(DatabaseUserDao.class.getName()).log(Level.SEVERE, null, ex);
            DataAccessException dax = new DataAccessException("Could not delete this user. "
                    + "There was a database error.", ex);

            throw dax;
        }

    }

}
