package bussineslogic;

import dataaccess.ProductDAO;
import model.Product;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductValidator {

    private static boolean validateName(Product c) {
        Pattern illegalExpression = Pattern.compile("[^A-Za-z \\d*]");
        Matcher regexMatcher = illegalExpression.matcher(c.getName());
        return !regexMatcher.find();
    }

    private static boolean validatePrice(Product c) {
        return (c.getPrice() > 0);
    }

    private static boolean validateStock(Product c) {
        return (c.getStock() > 0);
    }

    public static boolean insertProduct(Product c) {
        if (validateName(c) && validatePrice(c) && validateStock(c)) {
            ProductDAO a = new ProductDAO();
            return a.insert(c);
        }
        return false;
    }

    public static boolean updateProduct(Product c) {
        if (validateName(c) && validatePrice(c) && validateStock(c)) {
            ProductDAO a = new ProductDAO();
            return a.update(c, "id", c.getId());
        }
        return false;
    }

    public static boolean deleteProduct(int id) {
        ProductDAO a = new ProductDAO();
        return a.delete("id", id);
    }

    public static ArrayList<Object> getProduct(boolean all, int id){
        ProductDAO a = new ProductDAO();
        if(all) {
            return a.findAll();
        }
        else {
            return a.find("id", id);
        }
    }

}

