/*
    Inventory and User Mangement Web App
    This program creates a web app to allow users to manipulate data in inventory
    and user list.
    May 3, 2021
 */
package mags;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * This class creates setters and getters for Product object and uses 
 * Serializable to read and write objects to a database table.
 * 
 * @author leadermags
 */
public class Product implements Serializable {
    
    // declare variables
    private String upc;
    private String description;
    private BigDecimal price;
    private Integer stock;

    /**
     * 
     * Constructor with correct data types.
     * 
     * @param upc
     * @param desc
     * @param price
     * @param stock 
     */
    Product(String upc, String desc, BigDecimal price, int stock) {
        this.upc = upc;
        this.description = desc;
        this.price = price;
        this.stock = stock;
    }

    /**
     * Default constructor.
     */
    Product() {
        
    }

    /**
     * 
     * Constructor to convert String into correct data types.
     * 
     * @param upc
     * @param description
     * @param price
     * @param stock 
     */
    Product(String upc, String description, String price, String stock) {
        this.upc = upc;
        this.description = description;
        this.price = new BigDecimal(price);
        this.stock = Integer.parseInt(stock);
    }
    
    /**
     * 
     * This method sets the UPC.
     * @param upc 
     */
    public void setUpc(String upc){
        this.upc = upc;
    }
    
    /**
     * 
     * This method returns the UPC.
     * @return 
     */
    public String getUpc(){
        return upc;
    }
    
    /**
     * 
     * This method sets the description.
     * @param description 
     */
    public void setDescription(String description){
        this.description = description;
    }
    
    /**
     * 
     * This method returns the description.
     * @return 
     */
    public String getDescription(){
        return description;
    }
    
    /**
     * 
     * This method sets the price.
     * @param price 
     */
    public void setPrice(BigDecimal price){
        this.price  = price;
    }
    
    /**
     * 
     * This method returns the price.
     * @return 
     */
    public BigDecimal getPrice(){
        return price;
    }
    
    /**
     * 
     * This method sets the stock amount.
     * @param stock 
     */
    public void setStock(int stock){
        this.stock = stock;
    }
    
    /**
     * 
     * This method returns the stock amount.
     * @return 
     */
    public Integer getStock(){
        return stock;
    }
    
    
}
