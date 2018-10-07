package bussineslogic;
import dataaccess.CustomerDAO;
import dataaccess.OrderDateDAO;
import dataaccess.OrderDetailsDAO;
import dataaccess.ProductDAO;
import model.Customer;
import model.OrderDate;
import model.OrderDetails;
import model.Product;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderValidator {

    private static boolean validateName(Product c) {
        Pattern illegalExpression = Pattern.compile("[^A-Za-z \\d*]");
        Matcher regexMatcher = illegalExpression.matcher(c.getName());
        return !regexMatcher.find();
    }

    private static boolean validateDate(OrderDate c) {
        return (c.getDay() > 0 && c.getDay() < 32 && c.getMonth() > 0 && c.getMonth() < 13 && c.getYear() > 2018 && c.getYear() < 2020);
    }

    private static boolean validateQuantity(OrderDetails c) {
        ProductDAO a = new ProductDAO();
        Product p = (Product)a.find("id", c.getProductId()).get(0);
        return p.getStock() - c.getQuantity() >= 0;
    }

    private static boolean productExist(int id){
        ProductDAO a = new ProductDAO();
        return !a.find("id", id).isEmpty();
    }

    private static boolean clientExist(int id){
        CustomerDAO a = new CustomerDAO();
        return !a.find("id", id).isEmpty();
    }

    public static boolean insertOrder(OrderDate c, OrderDetails d) {
        boolean a1 = true;
        boolean a2;
        if (productExist(d.getProductId()) && clientExist(c.getClientId()) && validateDate(c) && validateQuantity(d)) {
            OrderDetailsDAO a = new OrderDetailsDAO();
            OrderDateDAO b = new OrderDateDAO();
            ProductDAO e = new ProductDAO();
            Product p = (Product)e.find("id", d.getProductId()).get(0);
            if(b.find("id", c.getId()).isEmpty()){
                a1 = b.insert(c);
            }
            a2 = a.insert(d);
            if(a1 && a2) {
                p.setStock(p.getStock() - d.getQuantity());
                e.update(p,"id", p.getId());
                return true;
            }
            return false;
        }
        return false;
    }

    public static boolean deleteOrder(int id){
        OrderDetailsDAO a = new OrderDetailsDAO();
        OrderDateDAO b = new OrderDateDAO();
        boolean a1 = a.delete("orderid", id);
        if(a1) {
            return b.delete("id", id);
        }
        return false;
    }

    public static ArrayList<Object> getOrderDate(boolean all, int id){
        OrderDateDAO a = new OrderDateDAO();
        if(all) {
            return a.findAll();
        }
        else {
            return a.find("id", id);
        }
    }

    public static ArrayList<Object> getOrderDetails(boolean all, int id){
        OrderDetailsDAO a = new OrderDetailsDAO();
        if(all) {
            return a.findAll();
        }
        else {
            return a.find("id", id);
        }
    }


    /**
     * metoda se ocupa cu generarea facutrii unei comenzi si scrie rezultatul intr-un fisier text
     * @param id primeste id-ul comenzii si genereaza factura aferenta comenzii
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public static void getBill(int id) throws FileNotFoundException, UnsupportedEncodingException {

        ArrayList<Object> list1;
        OrderDetailsDAO a = new OrderDetailsDAO();
        OrderDateDAO b = new OrderDateDAO();
        CustomerDAO c = new CustomerDAO();
        ProductDAO d = new ProductDAO();
        ProductDAO e = new ProductDAO();

        list1 = a.find("orderid",id);
        OrderDate f = (OrderDate)b.find("id", id).get(0);
        Customer g = (Customer)c.find("id", f.getClientId()).get(0);

        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        PrintWriter writer = new PrintWriter("Factura" + f.getId() + ".txt", "UTF-8");
        writer.println(timeStamp);
        writer.println("Nume: " + g.getName() + "| Adresa: " + g.getAddress() + "| Email: " + g.getEmail());
        writer.println("Produsele comandate:");
        int sum = 0;

        for(Object o:list1) {
            int productId = OrderDetails.class.cast(o).getProductId();
            Product p = (Product)d.find("id",productId).get(0);
            writer.println(p.getName() + " " + p.getPrice());
            sum += p.getPrice();
        }
        writer.println("Suma de plata " + sum);
        writer.close();
    }
}
