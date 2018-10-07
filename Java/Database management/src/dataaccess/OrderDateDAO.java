package dataaccess;

import model.OrderDate;

import java.util.ArrayList;

public class OrderDateDAO extends GenericDAO<OrderDate>{
    
    public ArrayList<Object> find(String field, int value){
        return super.find(field, value);
    }
    
    public ArrayList<Object> findAll(){
        return super.findAll(); 
    }

    public boolean insert(OrderDate p) {
        return super.insert(p);
    }

    public boolean update(OrderDate p, String field, int id) {
        return super.update(p, field, id);
    }

    public boolean delete(String field, int value) {
        return super.delete(field, value);
    }
        
}
