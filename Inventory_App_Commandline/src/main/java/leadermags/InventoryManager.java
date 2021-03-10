/*
    Maggie Lin
    Inventory App
    This program creates an Inventory Management System.
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
     * This method takes in a list to remove all nulls in the list by using an
     * iterator.
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
     * This method returns a list of products in the /inventory.dat file.
     * @return 
     */
    public static List<Product> getProductList() {

        try (FileInputStream fileIn = new FileInputStream(INVENTORY);
                ObjectInputStream ois = new ObjectInputStream(fileIn)) {
            
            // read all the data in the file
            Object data = ois.readObject();
            
            List<Product> products = (List<Product>) data;
            
            ois.close();

            return products;
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR: File not found. New file created. ");
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("ERROR: File not found. ");
        } 

        return new ArrayList<>();
    }

    /**
     * 
     * This method takes in Product and UPC to check if product is in list.
     * @param p
     * @param upc
     * @return
     * @throws IOException 
     */
    public Product getProduct(Product p, String upc) throws IOException {
        List<Product> products = getProductList();
        p.setUpc(upc);

        for (Product product : products) {
            if (product.getUpc().equals(p.getUpc())) {
                return p;
            }
        }
        return null; // product is not in list
    }

    /**
     * 
     * This method takes in UPC, description, price, and stock to update list.
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
        
        // try-catch for string unable to convert to BigDecimal then end 
        //      method if unable to convert
        try {
            p.setPrice(new BigDecimal(price));
        } catch (Exception e) {
            System.out.println("ERROR: Price not in valid input. "
                    + "No changes made.\n");
            return;
        }
        // try-catch for string unable to convert to integer then end 
        //      method if unable to convert
        try {
            p.setStock(Integer.parseInt(stock));
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Stock not in valid input. "
                    + "No changes made.\n");
            return;
        }

        List<Product> products = getProductList();

        // checks each product in list to prevent duplicate UPC
        for (Product product : products) {
            if (product.getUpc().equals(p.getUpc())) {
                System.out.println("ERROR: UPC already exists. "
                        + "Please select '3' from the menu.\n");
                return;
            }
        }

        products.add(p);

        // overwrite inventory.dat
        try (FileOutputStream fileOut = new FileOutputStream(INVENTORY);
                ObjectOutputStream oos = new ObjectOutputStream(fileOut)) {

            oos.writeObject(products);
            oos.close();
            System.out.println("Product added. Data saved in /inventory.dat\n");

        } catch (IOException ex) {
            System.out.println("ERROR: File not found. ");
        }
    }

    /**
     * 
     * This method takes in UPC and description to update the description of 
     * product with matching UPC.
     * @param upc
     * @param description
     * @throws IOException 
     */
    public void setDescription(String upc, String description) throws IOException {

        Product p = new Product();
        p.setUpc(upc);

        // calls getProduct() to check if UPC is in list
        Product checkProduct = getProduct(p, p.getUpc());
        try {

            // if UPC is in list, update product
            if (checkProduct != null) {

                List<Product> products = getProductList();

                // check each product in list to update product with 
                //      matching UPC
                for (Product product : products) {
                    if (product.getUpc().equals(p.getUpc())) {

                        p.setUpc(upc);
                        p.setDescription(description);
                        p.setPrice(product.getPrice());
                        p.setStock(product.getStock());

                        products.remove(products.indexOf(product));
                        
                        removeNulls(products);
                        
                        break;
                    }
                }

                products.add(p);

                // overwrite inventory.dat file
                try (FileOutputStream fileOut = new FileOutputStream(INVENTORY);
                        ObjectOutputStream oos = new ObjectOutputStream(fileOut)) {

                    oos.writeObject(products);
                    oos.close();
                    System.out.println("Description updated. Data saved in "
                            + "/inventory.dat\n");

                } catch (IOException i) {
                }
            } else {
                System.out.println("ERROR: UPC does not exist. "
                        + "Please select '2' from the menu.\n");
            }
        } catch (NullPointerException e) {
            System.out.println("ERROR: Product already deleted. "
                    + "Please select '2' from the menu to add it again.\n");
        }
    }
    
    /**
     * 
     * This method takes in UPC and price to update the price of product with
     * matching UPC.
     * @param upc
     * @param price
     * @throws IOException 
     */
    public void setPrice(String upc, String price) throws IOException {
        Product p = new Product();
        p.setUpc(upc);

        // calls getProduct() to check if UPC is in list
        Product checkProduct = getProduct(p, p.getUpc());
        try {

            // if UPC is in list, update product
            if (checkProduct != null) {

                List<Product> products = getProductList();

                // check each product in list to update product with 
                //      matching UPC
                for (Product product : products) {
                    if (product.getUpc().equals(p.getUpc())) {

                        p.setUpc(upc);
                        
                        
                        // try-catch for string unable to convert to BigDecimal
                        //      then end method if unable to convert
                        try {
                            p.setPrice(new BigDecimal(price));
                        } catch (Exception e) {
                            System.out.println("ERROR: Price not in valid input. "
                                    + "No changes made.\n");
                            return;
                        }
                        
                        p.setDescription(product.getDescription());
                        p.setStock(product.getStock());

                        products.remove(products.indexOf(product));
                        
                        removeNulls(products);
                        
                        break;
                    }
                }

                products.add(p);

                // overwrite inventory.dat file
                try (FileOutputStream fileOut = new FileOutputStream(INVENTORY);
                        ObjectOutputStream oos = new ObjectOutputStream(fileOut)) {

                    oos.writeObject(products);
                    oos.close();
                    System.out.println("Price updated. Data saved in "
                            + "/inventory.dat\n");

                } catch (IOException i) {
                }
            } else {
                System.out.println("ERROR: UPC does not exist. "
                        + "Please select '2' from the menu.\n");
            }
        } catch (NullPointerException e) {
            System.out.println("ERROR: Product already deleted. "
                    + "Please select '2' from the menu to add it again.\n");
        }
    }

    /**
     * 
     * This method takes in UPC and stock to update the stock of product with 
     * matching UPC.
     * @param upc
     * @param stock
     * @throws IOException 
     */
    public void setStock(String upc, String stock) throws IOException {
        
        Product p = new Product();
        p.setUpc(upc);

        // calls getProduct() to check if UPC is in list
        Product checkProduct = getProduct(p, p.getUpc());
        
        try {

            // if UPC is in list, update product
            if (checkProduct != null) {

                List<Product> products = getProductList();

                // check each product in list to update product with 
                //      matching UPC
                for (Product product : products) {

                    if (product.getUpc().equals(p.getUpc())) {

                        p.setDescription(product.getDescription());
                        p.setPrice(product.getPrice());
                        products.remove(products.indexOf(product));
                        
                        removeNulls(products);
                        
                        p.setUpc(upc);
                        
                        // try-catch for string unable to convert to integer
                        //      then end method if unable to convert
                        try {
                            p.setStock(Integer.parseInt(stock));
                        } catch (NumberFormatException e) {
                            System.out.println("ERROR: Stock not in valid input. "
                                    + "No changes made.\n");
                            return;
                        }
                        
                        break;
                    }
                }

                products.add(p);

                // overwrite inventory.dat file
                try (FileOutputStream fileOut = new FileOutputStream(INVENTORY);
                        ObjectOutputStream oos = new ObjectOutputStream(fileOut)) {

                    oos.writeObject(products);
                    oos.close();
                    System.out.println("ERROR updated. Data saved in "
                            + "/inventory.dat\n");

                } catch (IOException i) {
                }
            } else {
                System.out.println("ERROR: UPC does not exist. "
                        + "Please select '2' from the menu.\n");
            }
        } catch (NullPointerException e) {
            System.out.println("ERROR: Product already deleted. "
                    + "Please select '2' from the menu to add it again.\n");
        }
    }

    /**
     * 
     * This method takes in UPC and deletes the product with the UPC.
     * @param upc
     * @throws IOException 
     */
    public void deleteProduct(String upc) throws IOException {

        Product p = new Product();
        p.setUpc(upc);

        // calls getProduct() to check if UPC is in list
        Product checkProduct = getProduct(p, p.getUpc());
        
        try {

            // if UPC is in list, remove product
            if (checkProduct != null) {

                List<Product> products = getProductList();

                // check each product in list to remove product with matching UPC
                for (Product product : products) {
                    if (product.getUpc().equals(p.getUpc())) {

                        products.remove(products.indexOf(product));
                        
                        removeNulls(products); 

                        break;
                    }
                }

                // overwrite inventory.dat file
                try (FileOutputStream fileOut = new FileOutputStream(INVENTORY);
                        ObjectOutputStream oos = new ObjectOutputStream(fileOut)) {

                    oos.writeObject(products);
                    oos.close();
                    System.out.println("Product deleted. Data saved in "
                            + "/inventory.dat\n");

                } catch (IOException i) {
                }
            } else {
                System.out.println("ERROR: UPC does not exist.\n");
            }
        } catch (NullPointerException e) {
            System.out.println("ERROR: Product already deleted.\n");
        }
    }
}
