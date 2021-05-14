/*
    Inventory and User Mangement Web App
    This program creates a web app to allow users to manipulate data in inventory
    and user list.
    May 3, 2021
 */
package mags;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This is the ProductDAO and it implements the interface DAO for Product.
 * @author leadermags
 * @param <Product> 
 */
public class FileProductDao<Product> implements DataAccessObject<Product> {
    
    private List<Product> products = new ArrayList<>();

    @Override
    public List<Product> readAll() {
        return this.products;
    }

    @Override
    public Product read(Object id) {
        Product product = null;
        for(Product p: products){
            if(p.equals((Product) id)){
                return (Product) id;
            }
        }
        return product;
    }

    @Override
    public void create(Product e) {
        products.add(e);
    }

    @Override
    public void update(Product e) {
        Product product = null;
        for(Product p: products){
            if(p.equals(e)){
                product = p;
            } else {
            }
        }
        if(product != null){
            products.remove(product);
        }
        products.add(e);
    }

    @Override
    public void delete(Object id) {
        for(Product p: products){
            if(p.equals((Product) id)){
                products.remove((Product) id);
                break;
            }
        }
        
    }
    
}
