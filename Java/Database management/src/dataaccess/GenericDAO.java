package dataaccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GenericDAO<T> {

    private final Class<T> type;


    @SuppressWarnings("unchecked")
    GenericDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /** metoda utilizata pentru a crea iterogari primind ca parametru
     * @param fields parametru ce defineste campul
     */
    @SuppressWarnings({"StringConcatenationInsideStringBufferAppend", "StringBufferReplaceableByString"})
    private String createQuery(String fields){
        StringBuilder string = new StringBuilder();
        string.append("SELECT ");
        string.append(" * ");
        string.append(" FROM ");
        string.append(type.getSimpleName());
        string.append(" WHERE " + fields + " =?");
        return string.toString();
    }

    private String createQuery(){
        StringBuilder string = new StringBuilder();
        string.append("SELECT ");
        string.append(" * ");
        string.append(" FROM ");
        string.append(type.getSimpleName());
        return string.toString();
    }

    private String createDelete(String field) {
        StringBuilder string = new StringBuilder();
        string.append("DELETE");
        string.append(" FROM ");
        string.append(type.getSimpleName());
        string.append(" WHERE " + field + " =?");
        return string.toString();
    }

    /**metoda creaza sirul de caractere care este folosit pentru a
     * crea inserari in tabele     *
     * @param obj este un parametru generic care este folosit pentru identificarea tabelei in care va fi introdus
     * @return returneaza un sir de caractere reprezentand operatia de inserare
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */



    private String createInsert(T obj) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        StringBuilder string = new StringBuilder();
        string.append("INSERT INTO ").append(type.getSimpleName()).append(" (");
        int len = type.getDeclaredFields().length;
        int cur = len;
        for(Field field : type.getDeclaredFields()) {
            Object value = field.getName();
            string.append(value);
            if (cur != 1) {
                string.append(",");
            }
            cur--;
        }
        string.append(") VALUES (");
        cur = len;
        for(Field field : type.getDeclaredFields()) {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
            Method method = propertyDescriptor.getReadMethod();
            if(field.getType().getSimpleName().equals("String")) {
                string.append("'" + method.invoke(obj) + "'");
            }
            else {
                string.append(method.invoke(obj));
            }
            if (cur != 1) {
                string.append(",");
            }
            cur--;
        }
        string.append(")");
        return string.toString();
    }

    /**metoda este folosita pentru a crea sirul de caractere ce reprezeinta instructiunea de update in SQL     *
     * @param obj parametru generic ce va genera campurile pentru update
     * @param field1 parametru folosit pentru a face update pentru toate inregistrarile cu campul respectiv
     * @return returneaza un sir de caractere care reprezinta instructiunea
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */

    private String createUpdate(T obj, String field1) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        StringBuilder string = new StringBuilder();
        string.append("UPDATE ").append(type.getSimpleName()).append(" SET ");
        int len = type.getDeclaredFields().length;
        for(Field field : type.getDeclaredFields()) {
            Object value = field.getName();
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
            Method method = propertyDescriptor.getReadMethod();
            string.append(value);
            string.append(" = ");
            if(field.getType().getSimpleName().equals("String")) {
                string.append("'" + method.invoke(obj) + "'");
            }
            else {
                string.append(method.invoke(obj));
            }
            if(len != 1) {
                string.append(", ");
            }
            else {
                string.append(" ");
            }
            len--;
        }
        string.append("WHERE " + field1 + " = ?");
        System.out.println(string);
        return string.toString();
    }

    /**
     * metoda cauta si returneaza o lista de obiecte din baza de date corespunzatoare
     * @param field campul dupa care se face cautarea
     * @param value valoarea care o are campul cautat
     * @return returneaza o lista de obiecte
     */


    public ArrayList<Object> find(String field, int value) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnectivity.getConnection();
            statement = connection.prepareStatement(createQuery(field));
            statement.setInt(1, value);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            System.out.println("A avut loc o eroare!!!!");
        }
        return null;
    }

    /**
     *
     * @return returneaza toate obiectele din tabela respectiva
     */

    public ArrayList<Object> findAll(){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnectivity.getConnection();
            statement = connection.prepareStatement(createQuery());
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            System.out.println("A avut loc o eroare!!!!");
        }
        return null;
    }

    /**
     *
     * @param obj obiectul care vrem sa il inseram in tabela
     * @return returneaza true in caz de succes, altfel false
     */


    public boolean insert(T obj) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnectivity.getConnection();
            statement = connection.prepareStatement(createInsert(obj));
            statement.executeUpdate();
            return true;
        } catch (IllegalAccessException | InvocationTargetException | IntrospectionException | SQLException e) {
            return false;
        }
    }

    /**
     *
     * @param field campul dupa care se face stergerea
     * @param value valoarea pe care o are campul
     * @return returneaza true in caz de succes, altfel fals
     */

    public boolean delete(String field, int value) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            if(!find(field, value).isEmpty()) {
                connection = DatabaseConnectivity.getConnection();
                statement = connection.prepareStatement(createDelete(field));
                statement.setInt(1, value);
                statement.executeUpdate();
                return true;
            }
            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     *
     * @param obj obiectul care are valorile actualizate
     * @param field campul dupa care se cauta inregistrarea
     * @param value valoarea pe care o are campul
     * @return returneaza true in caz de succes, altfel fals
     */

    public boolean update(T obj, String field, int value){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DatabaseConnectivity.getConnection();
            statement = connection.prepareStatement(createUpdate(obj, field));
            statement.setInt(1, value);
            statement.executeUpdate();
            return !find(field, value).isEmpty();
        } catch (SQLException | IllegalAccessException | IntrospectionException | InvocationTargetException e) {
            return false;
        }
    }

    /**
     * metoda primeste ca parametru un rezultat dintr-o inregistrare, folosind metoda de reflexie se genereaza obiectele
     * @param resultSet reprezinta rezultatul generat in urma interogarii
     * @return returneaza lista de obiecte aferenta rezultatelor primite ca si parametru
     */

    private ArrayList<Object> createObjects(ResultSet resultSet) {
        ArrayList<Object> list = new ArrayList<>();
        try {
            while(resultSet.next()) {
                T instance = type.newInstance();
                for(Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (SQLException | IllegalAccessException | InstantiationException | InvocationTargetException | IntrospectionException e) {
            return null;
        }
        return list;
    }

}
