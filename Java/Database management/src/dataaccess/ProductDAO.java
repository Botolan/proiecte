package dataaccess;

import model.Product;

import java.util.ArrayList;

public class ProductDAO extends GenericDAO<Product> {

    public ArrayList<Object> find(String field, int id){
        return super.find(field, id);
    }

    public ArrayList<Object> findAll(){
        return super.findAll();
    }

    public boolean insert(Product p) {
        return super.insert(p);
    }

    public boolean update(Product p, String field, int value) {
        return super.update(p, field, value);
    }

    public boolean delete(String field, int value) {
        return super.delete(field, value);
    }


}