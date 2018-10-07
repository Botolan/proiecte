package dataaccess;

import model.Customer;

import java.util.ArrayList;

public class CustomerDAO extends GenericDAO<Customer> {

    public ArrayList<Object> find(String field, int value){
        return super.find(field, value);
    }

    public ArrayList<Object> findAll(){
        return super.findAll();
    }

    public boolean insert(Customer c) {
        return super.insert(c);
    }

    public boolean update(Customer c, String field, int value) {
        return super.update(c, field, value);
    }

    public boolean delete(String field, int value) {
        return super.delete(field, value);
    }

}
