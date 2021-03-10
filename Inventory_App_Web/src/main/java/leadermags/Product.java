/*
    Maggie Lin
    Module 2 Project
    Inventory Web App
    This program creates a web app to allow users to view the list in inventory
    and create, update, and delete a product to the inventory list.
    March 10, 2021
 */
package leadermags;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * This class creates setters and getters for Product object and uses 
 * Serializable to read and write objects to a file.
 * 
 * @author Maggie Lin 
 */
public class Product implements Serializable {
    
    // declare variables
    private String upc;
    private String description;
    private BigDecimal price;
    private Integer stock;
    
    /**
     * Constructor
     */
    public Product(){
        this.upc = upc;
        this.description = description;
        this.price = price;
        this.stock = stock;
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
