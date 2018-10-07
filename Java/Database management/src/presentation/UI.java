package presentation;

import bussineslogic.CustomerValidator;
import bussineslogic.OrderValidator;
import bussineslogic.ProductValidator;
import dataaccess.*;
import model.Customer;
import model.OrderDate;
import model.OrderDetails;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

@SuppressWarnings("Duplicates")
public class UI {
    private DefaultTableModel tableMode;
    private JTable table;
    private JLabel log;
    private JScrollPane scroll;


    private void userInterface(){
        JFrame frame = new JFrame("Depozit");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000,1000);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);


        JPanel mainPanel = new JPanel();
        mainPanel.setSize(new Dimension(1000,1000));


        JPanel selectTab = new JPanel();
        selectTab.setPreferredSize(new Dimension(1000,100));

        JButton customer = new JButton("Panou Client");
        JButton product = new JButton("Panou Produs");
        JButton order = new JButton("Panou Comanda");

        selectTab.add(customer);
        selectTab.add(product);
        selectTab.add(order);


        JPanel customerPanel = new JPanel();
        JPanel selectOptionC = new JPanel();
        JPanel insertDataC = new JPanel();
        customerPanel.setPreferredSize(new Dimension(1000,400));
        selectOptionC.setPreferredSize(new Dimension(1000,50));
        insertDataC.setPreferredSize(new Dimension(322,350));
        JButton viewAllc = new JButton("View All");
        JButton insertc = new JButton("Insereaza");
        JButton cautaIdc = new JButton("Cauta dupa id");
        JButton stergeIdc = new JButton("Sterge dupa id");
        JButton updateC = new JButton("Actualizeaza client");
        selectOptionC.add(viewAllc);
        selectOptionC.add(insertc);
        selectOptionC.add(cautaIdc);
        selectOptionC.add(stergeIdc);
        selectOptionC.add(updateC);

        JLabel campIdcL = new JLabel("ID client");
        JTextField campIdc = new JTextField("");
        campIdc.setPreferredSize(new Dimension(200,20));

        JLabel campNumecL = new JLabel("Nume client");
        JTextField campNumec = new JTextField("");
        campNumec.setPreferredSize(new Dimension(200,20));

        JLabel campAdresacL = new JLabel("Adresa client");
        JTextField campAdresac = new JTextField("");
        campAdresac.setPreferredSize(new Dimension(200,20));

        JLabel campEmailcL = new JLabel("Email client");
        JTextField campEmailc = new JTextField("");
        campEmailc.setPreferredSize(new Dimension(200,20));

        JLabel campVarstacL = new JLabel("Varsta client");
        JTextField campVarstac = new JTextField("");
        campVarstac.setPreferredSize(new Dimension(200,20));

        insertDataC.add(campIdcL);
        insertDataC.add(campIdc);
        insertDataC.add(campNumecL);
        insertDataC.add(campNumec);
        insertDataC.add(campAdresacL);
        insertDataC.add(campAdresac);
        insertDataC.add(campEmailcL);
        insertDataC.add(campEmailc);
        insertDataC.add(campVarstacL);
        insertDataC.add(campVarstac);
        customerPanel.add(selectOptionC);
        customerPanel.add(insertDataC);


        JPanel productPanel = new JPanel();
        JPanel selectOptionP = new JPanel();
        JPanel insertDataP = new JPanel();
        productPanel.setSize(new Dimension(1000,400));


        productPanel.setPreferredSize(new Dimension(1000,400));
        selectOptionP.setPreferredSize(new Dimension(1000,50));
        insertDataP.setPreferredSize(new Dimension(320,350));
        JButton viewAllp = new JButton("View All");
        JButton insertp = new JButton("Insereaza");
        JButton cautaIdp = new JButton("Cauta dupa id");
        JButton stergeIdp = new JButton("Sterge dupa id");
        JButton updatep = new JButton("Actualizeaza produs");
        selectOptionP.add(viewAllp);
        selectOptionP.add(insertp);
        selectOptionP.add(cautaIdp);
        selectOptionP.add(stergeIdp);
        selectOptionP.add(updatep);

        JLabel campIdpL = new JLabel("ID Produs");
        JTextField campIdp = new JTextField("");
        campIdp.setPreferredSize(new Dimension(200,20));

        JLabel campNumepL= new JLabel("Nume Produs");
        JTextField campNumep = new JTextField("");
        campNumep.setPreferredSize(new Dimension(200,20));

        JLabel campPretpL = new JLabel("Pret produs");
        JTextField campPretp = new JTextField("");
        campPretp.setPreferredSize(new Dimension(200,20));

        JLabel campStocpL = new JLabel("Cantitate stoc");
        JTextField campStocp = new JTextField("");
        campStocp.setPreferredSize(new Dimension(200,20));

        insertDataP.add(campIdpL);
        insertDataP.add(campIdp);
        insertDataP.add(campNumepL);
        insertDataP.add(campNumep);
        insertDataP.add(campPretpL);
        insertDataP.add(campPretp);
        insertDataP.add(campStocpL);
        insertDataP.add(campStocp);
        productPanel.add(selectOptionP);
        productPanel.add(insertDataP);



        JPanel orderPanel = new JPanel();
        JPanel selectOptionO = new JPanel();
        JPanel insertDataO = new JPanel();

        order.setSize(new Dimension(1000,400));



        orderPanel.setPreferredSize(new Dimension(1000,400));
        selectOptionO.setPreferredSize(new Dimension(1000,75));
        insertDataO.setPreferredSize(new Dimension(300,325));
        JButton viewAllDate = new JButton("View All Data");
        JButton viewAllDetails = new JButton("View All Detalii");
        JButton inserto = new JButton("Insereaza");
        JButton cautaIdDatao = new JButton("Cauta data comanda");
        JButton cautaIdDetaliio = new JButton("Cauta detalii comanda");
        JButton stergeIdo = new JButton("Sterge dupa id");
        JButton generateBill = new JButton("Factura comanda");
        selectOptionO.add(viewAllDate);
        selectOptionO.add(viewAllDetails);
        selectOptionO.add(inserto);
        selectOptionO.add(cautaIdDatao);
        selectOptionO.add(cautaIdDetaliio);
        selectOptionO.add(stergeIdo);
        selectOptionO.add(generateBill);

        JLabel campIdComandaoL = new JLabel("ID Comanda");
        JTextField campIdComandao = new JTextField("");
        campIdComandao.setPreferredSize(new Dimension(200,20));

        JLabel campIdComandaListoL = new JLabel("ID Lista");
        JTextField campIdComandaListo = new JTextField("");
        campIdComandaListo.setPreferredSize(new Dimension(200,20));

        JLabel campIdpOL = new JLabel("Id produs");
        JTextField campIdpo = new JTextField("");
        campIdpo.setPreferredSize(new Dimension(200,20));

        JLabel campIdcoL = new JLabel("Id client");
        JTextField campIdcO = new JTextField("");
        campIdcO.setPreferredSize(new Dimension(200,20));

        JLabel campCantitateoL = new JLabel("Cantitate");
        JTextField campCantitateO = new JTextField("");
        campCantitateO.setPreferredSize(new Dimension(200,20));

        JLabel campZioL = new JLabel("Zi comanda");
        JTextField campZiO = new JTextField("");
        campZiO.setPreferredSize(new Dimension(200,20));

        JLabel campLunaoL = new JLabel("Luna comanda");
        JTextField campLunaO = new JTextField("");
        campLunaO.setPreferredSize(new Dimension(200,20));

        JLabel campAnoL = new JLabel("An comanda");
        JTextField campAnO = new JTextField("");
        campAnO.setPreferredSize(new Dimension(200,20));

        insertDataO.add(campIdComandaoL);
        insertDataO.add(campIdComandao);
        insertDataO.add(campIdComandaListoL);
        insertDataO.add(campIdComandaListo);
        insertDataO.add(campIdpOL);
        insertDataO.add(campIdpo);
        insertDataO.add(campIdcoL);
        insertDataO.add(campIdcO);
        insertDataO.add(campCantitateoL);
        insertDataO.add(campCantitateO);
        insertDataO.add(campZioL);
        insertDataO.add(campZiO);
        insertDataO.add(campLunaoL);
        insertDataO.add(campLunaO);
        insertDataO.add(campAnoL);
        insertDataO.add(campAnO);
        orderPanel.add(selectOptionO);
        orderPanel.add(insertDataO);

        JPanel resultPanel = new JPanel();
        resultPanel.setPreferredSize(new Dimension(1000,500));
        table = new JTable();
        table.setCellSelectionEnabled(false);
        table.setPreferredSize(new Dimension(850,400));

        scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(850,400));
        resultPanel.add(scroll);
        log = new JLabel("");
        resultPanel.add(log);

        customer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(selectTab);
                mainPanel.add(customerPanel);
                mainPanel.add(resultPanel);
                scroll.setVisible(false);
                frame.setContentPane(mainPanel);
            }
        });

        viewAllc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTable(CustomerValidator.getCustomer(true, 0));
                if(tableMode != null) {
                    table.setModel(tableMode);
                    scroll.setVisible(true);
                }
                else {
                    scroll.setVisible(false);
                    log.setText("Nu exista elemente in tabela");
                }
                frame.setContentPane(mainPanel);
            }
        });

        insertc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int clientId;
                String numeClient;
                String adresaClient;
                String emailClient;
                int varstaClient;
                try {
                    clientId = Integer.parseInt(campIdc.getText());
                    numeClient = campNumec.getText();
                    adresaClient = campAdresac.getText();
                    emailClient = campEmailc.getText();
                    varstaClient = Integer.parseInt(campVarstac.getText());
                    Customer c = new Customer(clientId, numeClient, adresaClient, emailClient, varstaClient);
                    if(!CustomerValidator.insertCustomer(c)){
                        log.setText("Eroare la introducerea clientului");
                    }
                    else {
                        log.setText("Clientul a fost adaugat cu success");
                    }
                }
                catch(NumberFormatException error) {
                    log.setText("Date instroduse sunt invalide");
                }
            }
        });

        updateC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int clientId = Integer.parseInt(campIdc.getText());
                    String numeClient = campNumec.getText();
                    String adresaClient = campAdresac.getText();
                    String emailClient = campEmailc.getText();
                    int varstaClient = Integer.parseInt(campVarstac.getText());
                    Customer c = new Customer(clientId, numeClient, adresaClient, emailClient, varstaClient);
                    if(!CustomerValidator.updateCustomer(c)){
                        log.setText("Eroare la actualizarea clientului");
                    }
                    else {
                        log.setText("Clientul a fost actualizat cu success");
                    }
                }
                catch(NumberFormatException error) {
                    log.setText("Date instroduse sunt invalide");
                }
            }
        });

        cautaIdc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int clientId = Integer.parseInt(campIdc.getText());
                ArrayList<Object> list = CustomerValidator.getCustomer(false, clientId);
                if(!list.isEmpty()) {
                    createTable(list);
                    if(tableMode != null) {
                        table.setModel(tableMode);
                        scroll.setVisible(true);
                    }
                    else {
                        scroll.setVisible(false);
                        log.setText("Nu exista elemente in tabela");
                    }
                    frame.setContentPane(mainPanel);
                }
                else {
                    scroll.setVisible(false);
                    log.setText("Nu s-a gasit elementul cautat");
                }
            }
        });

        stergeIdc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int clientId = Integer.parseInt(campIdc.getText());
                    scroll.setVisible(false);
                    if(!CustomerValidator.deleteCustomer(clientId)) {
                        log.setText("Eroare la stergerea clientului");
                    }
                    else {
                        log.setText("Client a fost sters cu succes");
                    }
                }
                catch(NumberFormatException error) {
                    log.setText("Date instroduse sunt invalide");
                }
            }
        });

        product.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(selectTab);
                mainPanel.add(productPanel);
                mainPanel.add(resultPanel);
                scroll.setVisible(false);
                frame.setContentPane(mainPanel);
            }
        });

        viewAllp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTable(ProductValidator.getProduct(true, 0));
                if(tableMode != null) {
                    table.setModel(tableMode);
                    scroll.setVisible(true);
                }
                else {
                    scroll.setVisible(false);
                    log.setText("Nu exista elemente in tabela");
                }
                frame.setContentPane(mainPanel);
            }
        });

        insertp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int produsId = Integer.parseInt(campIdp.getText());
                    String numeProdus = campNumep.getText();
                    Double pret = Double.parseDouble(campPretp.getText());
                    int stoc = Integer.parseInt(campStocp.getText());
                    Product c = new Product(produsId, numeProdus, pret, stoc);
                    if(!ProductValidator.insertProduct(c)){
                        log.setText("Eroare la introducerea produsului");
                    }
                    else {
                        log.setText("Produsul a fost adaugat cu success");
                    }
                }
                catch(NumberFormatException error) {
                    log.setText("Date instroduse sunt invalide");
                }
            }

        });

        cautaIdp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int produsId = Integer.parseInt(campIdp.getText());
                ArrayList<Object> list = ProductValidator.getProduct(false, produsId);
                if(!list.isEmpty()) {
                    createTable(list);
                    table.setModel(tableMode);
                    scroll.setVisible(true);
                    frame.setContentPane(mainPanel);
                }
                else {
                    scroll.setVisible(false);
                    log.setText("Nu s-a gasit elementul cautat");
                }
            }
        });

        stergeIdp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int produsId = Integer.parseInt(campIdp.getText());
                    scroll.setVisible(false);
                    if (!ProductValidator.deleteProduct(produsId)) {
                        log.setText("Eroare la stergerea produsului");
                    } else {
                        log.setText("Produsul a fost sters cu succes");
                    }
                } catch (NumberFormatException error) {
                    log.setText("Date instroduse sunt invalide");
                }
            }
        });

        updatep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int produsId = Integer.parseInt(campIdp.getText());
                    String numeProdus = campNumep.getText();
                    Double pret = Double.parseDouble(campPretp.getText());
                    int stoc = Integer.parseInt(campStocp.getText());
                    Product c = new Product(produsId, numeProdus, pret, stoc);
                    if(!ProductValidator.updateProduct(c)){
                        log.setText("Eroare la actualizarea produsului");
                    }
                    else {
                        log.setText("Produsul a fost actualizat cu success");
                    }
                }
                catch(NumberFormatException error) {
                    log.setText("Date instroduse sunt invalide");
                }
            }
        });

        order.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(selectTab);
                mainPanel.add(orderPanel);
                mainPanel.add(resultPanel);
                scroll.setVisible(false);
                frame.setContentPane(mainPanel);
            }
        });

        viewAllDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTable(OrderValidator.getOrderDate(true, 0));
                if(tableMode != null) {
                    table.setModel(tableMode);
                    scroll.setVisible(true);
                }
                else {
                    scroll.setVisible(false);
                    log.setText("Nu exista elemente in tabela");
                }
                frame.setContentPane(mainPanel);
            }
        });

        viewAllDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTable(OrderValidator.getOrderDetails(true, 0));
                if(tableMode != null) {
                    table.setModel(tableMode);
                    scroll.setVisible(true);
                }
                else {
                    scroll.setVisible(false);
                    log.setText("Nu exista elemente in tabela");
                }
                frame.setContentPane(mainPanel);
            }
        });

        cautaIdDatao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int orderId = Integer.parseInt(campIdComandao.getText());
                ArrayList<Object> list = OrderValidator.getOrderDate(false, orderId);
                if (!list.isEmpty()) {
                    createTable(list);
                    if (tableMode != null) {
                        table.setModel(tableMode);
                        scroll.setVisible(true);
                    } else {
                        scroll.setVisible(false);
                        log.setText("Nu exista elemente in tabela");
                    }
                    frame.setContentPane(mainPanel);
                } else {
                    scroll.setVisible(false);
                    log.setText("Nu exista elemente in tabela");
                }
            }
        });

        cautaIdDetaliio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int orderId = Integer.parseInt(campIdComandao.getText());
                ArrayList<Object> list = OrderValidator.getOrderDetails(false, orderId);
                if (!list.isEmpty()) {
                    createTable(list);
                    if (tableMode != null) {
                        table.setModel(tableMode);
                        scroll.setVisible(true);
                    } else {
                        scroll.setVisible(false);
                        log.setText("Nu exista elemente in tabela");
                    }
                    frame.setContentPane(mainPanel);
                } else {
                    scroll.setVisible(false);
                    log.setText("Nu exista elemente in tabela");
                }
            }
        });

        inserto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int idComanda = Integer.parseInt(campIdComandao.getText());
                    int idLista = Integer.parseInt(campIdComandaListo.getText());
                    int campIdp = Integer.parseInt(campIdpo.getText());
                    int idClient = Integer.parseInt(campIdcO.getText());
                    int cantitate = Integer.parseInt(campCantitateO.getText());
                    int zi = Integer.parseInt(campZiO.getText());
                    int luna = Integer.parseInt(campLunaO.getText());
                    int an = Integer.parseInt(campAnO.getText());
                    OrderDetails a = new OrderDetails(idLista, idComanda, campIdp, cantitate);
                    OrderDate b = new OrderDate(idComanda, zi, luna, an, idClient);
                    if(OrderValidator.insertOrder(b, a)){
                        log.setText("Comanda realizata cu success");
                    }
                    else {
                        log.setText("Eroare la crearea comenzii");
                    }
                }
                catch(NumberFormatException error) {
                    log.setText("Date instroduse sunt invalide");
                }
            }
        });

        stergeIdo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int idComanda = Integer.parseInt(campIdComandao.getText());
                    if(OrderValidator.deleteOrder(idComanda)) {
                        log.setText("Comanda a fost stearsa cu succes");
                    }
                    else {
                        log.setText("Comanda nu a putu fi stearsa");
                    }
                }
                catch(NumberFormatException error) {
                    log.setText("Date instroduse sunt invalide");
                }

            }
        });

        generateBill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrintWriter writer = null;
                try {
                    int idComanda = Integer.parseInt(campIdComandao.getText());
                    try {
                        OrderValidator.getBill(idComanda);
                    } catch (FileNotFoundException | UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    }
                }
                catch(NumberFormatException error) {
                    log.setText("Date instroduse sunt invalide");
                }
            }
        });




        mainPanel.add(selectTab);

        frame.add(mainPanel);
        frame.setVisible(true);
        frame.setContentPane(mainPanel);
    }

    /**
     * @param objects parametrul contine o lista de obiecte
     * @param index reprezinta numarul de coloane pe care tabela le are si ne permite construirea tabelului cu un numar
     *              variabil de coloane
     * @return returneaza o matrice de elemente
     */

    private Object[][] getData(ArrayList<Object> objects, int index){
        int row = 0;
        int column = 0;
        Object data[][] = new Object[100][index + 1];
        for (Object o : objects) {
            for (Field field : objects.get(0).getClass().getDeclaredFields()) {
                try {
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), objects.get(0).getClass());
                    Method method = propertyDescriptor.getReadMethod();
                    data[row][column] = method.invoke(o);
                } catch (IllegalAccessException | InvocationTargetException | IntrospectionException e) {
                    e.printStackTrace();
                }
                column++;
            }
            column = 0;
            row++;
        }
        return data;
    }

    /** metoda se ocupa cu extragerea datelor din lista si crea tabelului cu ajutorul datelor extrase cu ajutorul metodei
     * getData();
     * @param objects parametrul contine o lista de obiecte
     */

    @SuppressWarnings("unchecked")
    private void createTable(ArrayList<Object> objects){
        log.setText("");
        if(!objects.isEmpty()) {
            ArrayList<String> list = new ArrayList<String>();
            int index = 0, row = 0, column = 0;
            for (Field field : objects.get(0).getClass().getDeclaredFields()) {
                list.add(field.getName());
            }
            String[] columnName = new String[list.size()];
            for(String s:list) {
                columnName[index] = s;
                index++;
            }
            Object data[][] = getData(objects, index);
            if(data.length != 0) {
                tableMode = new DefaultTableModel(data, columnName);
            }
            else {
                tableMode = null;
            }
        }
        else {
            tableMode = null;
        }
    }

    public static void main(String[] args) {
        UI ui = new UI();
        ui.userInterface();
    }

}
