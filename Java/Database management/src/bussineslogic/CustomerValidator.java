package bussineslogic;

import dataaccess.CustomerDAO;
import model.Customer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerValidator {


    private static boolean validateName(Customer c){
        Pattern illegalExpression = Pattern.compile("[^A-Za-z ]");
        Matcher regexMatcher = illegalExpression.matcher(c.getName());
        return !regexMatcher.find();
    }

    private static boolean validateAge(Customer c){
        return (c.getAge() >= 18 && c.getAge() <= 55);
    }

    private static boolean validateEmail(Customer c) {
        Pattern expression = Pattern.compile("(\\d*[A-Za-z]*\\d*)([@])([A-Za-z])*[.]([A-Za-z])*");
        Matcher regexMatcher = expression.matcher(c.getEmail());
        return regexMatcher.find();
    }

    public static boolean insertCustomer(Customer c){
        if(validateName(c) && validateAge(c) && validateEmail(c)){
            CustomerDAO a = new CustomerDAO();
            return a.insert(c);
        }
        return false;
    }

    public static boolean updateCustomer(Customer c) {
        if(validateName(c) && validateAge(c) && validateEmail(c)){
            CustomerDAO a = new CustomerDAO();
            return a.update(c,"id", c.getId());
        }
        return false;
    }

    public static boolean deleteCustomer(int id) {
        CustomerDAO a = new CustomerDAO();
        return a.delete("id", id);
    }

    public static ArrayList<Object> getCustomer(boolean all, int id){
        CustomerDAO a = new CustomerDAO();
        if(all) {
            return a.findAll();
        }
        else {
            return a.find("id", id);
        }
    }

}
