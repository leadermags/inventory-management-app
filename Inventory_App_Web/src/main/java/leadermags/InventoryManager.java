/*
    Maggie Lin
    Inventory Web App
    This program creates a web app to allow users to view the list in inventory
    and create, update, and delete a product to the inventory list.
 */
package leadermags;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * This class helps the app manage the inventory.
 *
 * @author Maggie Lin 
 */
public class InventoryManager extends Product {

    private static final String INVENTORY = System.getProperty("user.home")
            + "/inventory.dat";

    /**
     *
     * This method returns a list of products in the /inventory.dat file.
     *
     * @return
     */
    public List<Product> getProductList() {

        try ( FileInputStream fileIn = new FileInputStream(INVENTORY);  
                ObjectInputStream ois = new ObjectInputStream(fileIn)) {

            // read all the data in the file
            Object data = ois.readObject();

            List<Product> products = (List<Product>) data;

            ois.close();

            return products;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ArrayList<>();
    }

    /**
     *
     * This method takes in UPC, description, price, and stock to update list.
     *
     * @param upc
     * @param description
     * @param price
     * @param stock
     * @throws IOException
     */
    public void addProduct(String upc, String description, String price, String stock) throws IOException {

        Product p = new Product();
        p.setUpc(upc);
        p.setDescription(description);
        p.setPrice(new BigDecimal(price));
        p.setStock(Integer.parseInt(stock));

        List<Product> products = getProductList();
        
        if (getProduct(upc) != null) {
            return;
        }

        products.add(p);

        // write to inventory.dat
        writeData(products);
    }

    /**
     *
     * This method takes in Product and UPC to check if product is in list.
     *
     * @param upc
     * @return
     * @throws IOException
     */
    public Product getProduct(String upc) throws IOException {
        List<Product> products = getProductList();

        for (Product product : products) {
            if (product.getUpc().equals(upc)) {
                return product;
            }
        }
        return null; // product is not in list
    }

    /**
     *
     * This method takes in the parameters to update product with matching UPC.
     *
     * @param upc
     * @param description
     * @param price
     * @param stock
     * @throws IOException
     */
    public void editProduct(String upc, String description, String price, String stock) throws IOException {

        // Create new object
        Product p = new Product();

        p.setUpc(upc);

        // get Product List
        List<Product> products = getProductList();

        // find product in list to update product with matching UPC
        for (Product product : products) {
            if (product.getUpc().equals(p.getUpc())) {

                p.setUpc(upc);
                p.setDescription(description);
                p.setPrice(new BigDecimal(price));
                p.setStock(Integer.parseInt(stock));

                products.remove(products.indexOf(product));

                removeNulls(products);

                break;
            }
        }

        products.add(p);

        // write to inventory.dat file
        writeData(products);
    }

    /**
     *
     * This method takes in UPC and deletes the product with the UPC.
     *
     * @param upc
     * @throws IOException
     */
    public void deleteProduct(String upc) throws IOException {

        // Creates new object
        Product p = new Product();

        p.setUpc(upc);

        List<Product> products = getProductList();

        // find product in list to remove product with matching UPC
        for (Product product : products) {
            if (product.getUpc().equals(p.getUpc())) {

                products.remove(products.indexOf(product));

                removeNulls(products);

                break;
            }
        }

        // write to inventory.dat file
        writeData(products);
    }

    /**
     *
     * This method takes in a list to remove all nulls in the list by using an
     * iterator.
     *
     * @param <T>
     * @param list
     */
    public static <T> void removeNulls(List<T> list) {
        Iterator<T> itr = list.iterator();
        while (itr.hasNext()) {
            T curr = itr.next();

            if (curr == null) {
                itr.remove();
            }
        }
    }

    /**
     *
     * This method takes in a list of products then writes into inventory.dat
     *
     * @param products
     */
    public static void writeData(List<Product> products) {
        try ( FileOutputStream fileOut = new FileOutputStream(INVENTORY);  
                ObjectOutputStream oos = new ObjectOutputStream(fileOut)) {

            oos.writeObject(products);
            oos.close();

        } catch (IOException ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
