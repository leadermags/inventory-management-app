/*
    Maggie Lin
    Inventory App
    This program creates an Inventory Management System.
 */
package leadermags;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * This is the main app and provides a menu for the user to manage inventory.
 * @author Maggie Lin 
 */
public class InventoryApp {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);

        InventoryManager im = new InventoryManager();

        String menuInput;

        do {
            String upc;
            String description;
            String price;
            String stock;

            System.out.println("Please choose from the following: \n"
                    + "1 - View Products\n"
                    + "2 - Add Product\n"
                    + "3 - Update Product\n"
                    + "4 - Remove Product\n"
                    + "5 - Exit");

            menuInput = sc.nextLine();

            switch (menuInput) {

                // View Products option
                case "1":
                    if (InventoryManager.getProductList().isEmpty()) {
                        System.out.println("There is nothing in the inventory.\n");
                    } else {
                        
                        // prints all the objects in list
                        System.out.println(InventoryManager.getProductList() + "\n");
                        System.out.println("Here are the Products in the list in"
                                + " the format of \n"
                                + "'UPC---Description---Price---Stock': \n");
                        
                        // prints each product in list as string
                        for (Product product : InventoryManager.getProductList()) {
                            System.out.println(product.getUpc() + "\t"
                                    + product.getDescription() + "\t$"
                                    + product.getPrice().toString() + "\t"
                                    + product.getStock().toString() + "\n");
                        }
                    }
                    break;

                // Add Products option
                case "2":

                    System.out.println("Please enter the UPC of the product you "
                            + "would like to add: ");
                    sc = new Scanner(System.in);
                    upc = sc.nextLine();

                    System.out.println("Please enter the description of the "
                            + "product: ");
                    sc = new Scanner(System.in);
                    description = sc.nextLine();

                    System.out.println("Please enter the price of the product: ");
                    sc = new Scanner(System.in);
                    price = sc.nextLine();

                    System.out.println("Please enter the amount in stock of the "
                            + "product: ");
                    sc = new Scanner(System.in);
                    stock = sc.nextLine();

                    // calls addProduct(...) to add new product
                    im.addProduct(upc, description, price, stock);

                    break;
                    
                // Update Product option
                case "3":

                    String updateChoice;

                    System.out.println("Please enter the UPC of the product you "
                            + "would like to update: ");
                    sc = new Scanner(System.in);
                    upc = sc.nextLine();

                    System.out.println("Do you want to update the (d)escription,"
                            + " (p)rice, or (s)tock? ");
                    sc = new Scanner(System.in);
                    updateChoice = sc.nextLine();

                    switch (updateChoice.toUpperCase()) {

                        // user wants to update description
                        case "D":
                            System.out.println("Please enter the updated "
                                    + "description of " + upc);
                            sc = new Scanner(System.in);
                            description = sc.nextLine();
                            // calls setDescription(...) to update product's
                            //      description
                            im.setDescription(upc, description);
                            break;
                        
                        // user wants to update price
                        case "P":
                            System.out.println("Please enter the updated price "
                                    + "of " + upc);
                            sc = new Scanner(System.in);
                            price = sc.nextLine();
                            // calls setPrice(...) to update product's price
                            im.setPrice(upc, price);
                            break;
                        
                        // user wants to update stock
                        case "S":
                            System.out.println("Please enter the updated amount "
                                    + "in stock of " + upc);
                            sc = new Scanner(System.in);
                            stock = sc.nextLine();
                            // calls setStock(...) to update product's stock
                            im.setStock(upc, stock);
                            break;
                        
                        // user did not enter a valid option
                        default:
                            System.out.println("ERROR: You did not choose a "
                                    + "valid option. Please try again and input "
                                    + "'d' or 'p' or 's'. \n");
                            break;
                    }
                    break;
                    
                // Remove Product option
                case "4":

                    System.out.println("Please input the UPC of the product you "
                            + "would like to remove: ");
                    upc = sc.nextLine();
                    // calls deleteProduct(...) to delete product with matching
                    //      UPC
                    im.deleteProduct(upc);

                    break;
                    
                // Exit option
                case "5":
                    System.out.println("You have ended the program. ");
                    System.exit(0);
                    break;
                    
                // default option if user did not enter a valid option
                default:
                    System.out.println("ERROR: Please enter a valid option. \n");
                    break;
            }
        } while (!menuInput.equals("5")); // ends loop when user selects Exit
    }
}
