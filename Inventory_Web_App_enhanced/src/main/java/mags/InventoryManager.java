/*
    Inventory and User Mangement Web App
    This program creates a web app to allow users to manipulate data in inventory
    and user list.
    May 3, 2021
 */
package mags;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * This class helps the app manage the inventory.
 *
 * @author leadermags
 */
public class InventoryManager {

    private final DataAccessObject<Product> productDao;

    public InventoryManager() throws DataAccessException {
        this.productDao = DataAccessObjectFactory.getProductDao();
    }

    /**
     *
     * This method returns a list of products.
     *
     * @return
     * @throws DataAccessException
     */
    public List<Product> getProductList() throws DataAccessException {
        return productDao.readAll();
    }

    /**
     *
     * This method takes in UPC, description, price, and stock to update list.
     *
     * @param upc
     * @param description
     * @param price
     * @param stock
     * @throws DataAccessException
     */
    public void addProduct(String upc, String description, String price, String stock) throws DataAccessException{

        Product p = new Product(upc, description, price, stock);
        
        productDao.create(p);

    }

    /**
     *
     * This method takes in Product and UPC to check if product is in list.
     *
     * @param upc
     * @return
     * @throws DataAccessException
     */
    public Product getProduct(String upc) throws DataAccessException{
        return productDao.read(upc);
    }

    /**
     *
     * This method takes in the parameters to update product with matching UPC.
     *
     * @param upc
     * @param description
     * @param price
     * @param stock
     * @throws DataAccessException
     */
    public void editProduct(String upc, String description, String price, String stock) throws DataAccessException{

        // Create new object
        Product p = new Product();

        p.setUpc(upc);
        p.setDescription(description);
        p.setPrice(new BigDecimal(price));
        p.setStock(Integer.parseInt(stock));
        
        productDao.update(p);

    }

    /**
     *
     * This method takes in UPC and deletes the product with the UPC.
     *
     * @param upc
     * @throws IOException
     * @throws DataAccessException
     */
    public void deleteProduct(String upc) throws IOException, DataAccessException {

        productDao.delete(upc);

    }

}
