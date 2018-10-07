package dataaccess;

import model.OrderDetails;

import java.util.ArrayList;

public class OrderDetailsDAO extends GenericDAO<OrderDetails>{

    public ArrayList<Object> find(String field, int value){
        return super.find(field, value);
    }

    public ArrayList<Object> findAll(){
        return super.findAll();
    }

    public boolean insert(OrderDetails p) {
        return super.insert(p);
    }

    public boolean update(OrderDetails p, String field, int value) {
        return super.update(p, field, value);
    }

    public boolean delete(String field, int value) {
        return super.delete(field, value);
    }

}
