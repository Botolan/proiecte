import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import bank.Bank;
import objects.Account;
import objects.Person;
import objects.SavingAccount;
import objects.SpendingAccount;

@SuppressWarnings("Duplicates")
public class GUI {

    private DefaultTableModel tableMode;
    private DefaultTableModel tableModePersoana;
    private JTable table = new JTable();
    private JTable tablePersoana = new JTable();
    private JScrollPane scroll;
    private JScrollPane scrollPersoana;
    private Bank bank;
    private JLabel log = new JLabel("");

    private GUI() throws IOException, ClassNotFoundException {
        bank = new Bank();
    }


    private void userInterface(){

        String[] tipCont = {"Cont cumparaturi", "Cont economii"};

        JFrame frame = new JFrame("Depozit");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000,1000);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JFrame frameTabel = new JFrame("Persoana");
        frameTabel.setSize(1000, 100);
        frameTabel.setLocationRelativeTo(null);
        frameTabel.setResizable(false);

        JPanel logPanel = new JPanel();
        logPanel.setPreferredSize(new Dimension(1000,50));
        logPanel.add(log);

        JPanel panelTabel = new JPanel();
        scrollPersoana = new JScrollPane(tablePersoana);
        scrollPersoana.setPreferredSize(new Dimension(850, 50));
        panelTabel.add(scrollPersoana);

        frameTabel.add(panelTabel);


        JPanel mainPanel = new JPanel();
        mainPanel.setSize(new Dimension(1000,1000));


        JPanel selectTab = new JPanel();
        selectTab.setPreferredSize(new Dimension(1000,100));

        JButton customer = new JButton("Panou Client");
        JButton account = new JButton("Panou Conturi");
        JButton operation = new JButton("Panou Operatii");

        selectTab.add(customer);
        selectTab.add(account);
        selectTab.add(operation);


        JPanel customerPanel = new JPanel();
        JPanel selectOptionC = new JPanel();
        JPanel executeOptionCS = new JPanel();
        JPanel executeOptionCI = new JPanel();
        JPanel executeOptionCD = new JPanel();
        JPanel executeOptionCU = new JPanel();
        JPanel insertDataAddC = new JPanel();
        JPanel insertIdSearch = new JPanel();
        JPanel insertIdDelete = new JPanel();
        JPanel insertDataUpdateC = new JPanel();

        customerPanel.setPreferredSize(new Dimension(1000,400));
        selectOptionC.setPreferredSize(new Dimension(1000,50));
        insertDataAddC.setPreferredSize(new Dimension(322,130));
        insertDataUpdateC.setPreferredSize(new Dimension(322, 130));
        JButton viewAllc = new JButton("Afiseaza clienti");
        JButton insertc = new JButton("Insereaza client");
        JButton cautaIdc = new JButton("Cauta dupa id");
        JButton stergeIdc = new JButton("Sterge dupa id");
        JButton updateC = new JButton("Actualizeaza client");
        JButton executeCommandCS = new JButton("Executa");
        JButton executeCommandCI = new JButton("Executa");
        JButton executeCommandCD = new JButton("Executa");
        JButton executeCommandCU = new JButton("Executa");

        selectOptionC.add(viewAllc);
        selectOptionC.add(insertc);
        selectOptionC.add(cautaIdc);
        selectOptionC.add(stergeIdc);
        selectOptionC.add(updateC);

        executeOptionCS.add(executeCommandCS);
        executeOptionCI.add(executeCommandCI);
        executeOptionCD.add(executeCommandCD);
        executeOptionCU.add(executeCommandCU);

        JLabel campIdcLD = new JLabel("ID client");
        JTextField campIdcD = new JTextField("");
        campIdcD.setPreferredSize(new Dimension(200,20));

        JLabel campIdcLS = new JLabel("ID client");
        JTextField campIdcS = new JTextField("");
        campIdcS.setPreferredSize(new Dimension(200,20));

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


        JLabel campIdcLU = new JLabel("ID client");
        JTextField campIdcU = new JTextField("");
        campIdcU.setPreferredSize(new Dimension(200,20));

        JLabel campNumecLU = new JLabel("Nume client");
        JTextField campNumecU = new JTextField("");
        campNumecU.setPreferredSize(new Dimension(200,20));

        JLabel campAdresacLU = new JLabel("Adresa client");
        JTextField campAdresacU = new JTextField("");
        campAdresacU.setPreferredSize(new Dimension(200,20));

        JLabel campEmailcLU = new JLabel("Email client");
        JTextField campEmailcU = new JTextField("");
        campEmailcU.setPreferredSize(new Dimension(200,20));

        JLabel campVarstacLU = new JLabel("Varsta client");
        JTextField campVarstacU = new JTextField("");
        campVarstacU.setPreferredSize(new Dimension(200,20));

        insertDataAddC.add(campIdcL);
        insertDataAddC.add(campIdc);
        insertDataAddC.add(campNumecL);
        insertDataAddC.add(campNumec);
        insertDataAddC.add(campAdresacL);
        insertDataAddC.add(campAdresac);
        insertDataAddC.add(campEmailcL);
        insertDataAddC.add(campEmailc);
        insertDataAddC.add(campVarstacL);
        insertDataAddC.add(campVarstac);

        insertDataUpdateC.add(campIdcLU);
        insertDataUpdateC.add(campIdcU);
        insertDataUpdateC.add(campNumecLU);
        insertDataUpdateC.add(campNumecU);
        insertDataUpdateC.add(campAdresacLU);
        insertDataUpdateC.add(campAdresacU);
        insertDataUpdateC.add(campEmailcLU);
        insertDataUpdateC.add(campEmailcU);
        insertDataUpdateC.add(campVarstacLU);
        insertDataUpdateC.add(campVarstacU);

        insertIdSearch.add(campIdcLS);
        insertIdSearch.add(campIdcS);

        insertIdDelete.add(campIdcLD);
        insertIdDelete.add(campIdcD);

        customerPanel.add(selectOptionC);
        customerPanel.add(insertDataAddC);


        JPanel resultPanel = new JPanel();
        scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(850,400));
        resultPanel.add(scroll);

        customer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(selectTab);
                mainPanel.add(selectOptionC);
                frame.setContentPane(mainPanel);
            }
        });

        viewAllc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(selectTab);
                mainPanel.add(selectOptionC);
                mainPanel.add(resultPanel);
                createTablePerson(bank.viewPersons());
                if(tableMode != null) {
                    table.setModel(tableMode);
                    scroll.setVisible(true);
                }
                else {
                    scroll.setVisible(false);
                }
                frame.setContentPane(mainPanel);
            }
        });
        
        cautaIdc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(selectTab);
                mainPanel.add(selectOptionC);
                mainPanel.add(insertIdSearch);
                mainPanel.add(executeOptionCS);

                frame.setContentPane(mainPanel);
            }
        });


        executeCommandCS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(selectTab);
                mainPanel.add(selectOptionC);
                mainPanel.add(insertIdSearch);
                mainPanel.add(executeOptionCS);
                mainPanel.add(resultPanel);
                log.setText("");
                mainPanel.add(logPanel);
                try {
                    if(Integer.parseInt(campIdcS.getText()) < 0) {
                        throw new IllegalArgumentException();
                    }
                    createTablePerson(bank.viewPerson(Integer.parseInt(campIdcS.getText())));
                    if (tableMode != null) {
                        table.setModel(tableMode);
                        scroll.setVisible(true);
                    } else {
                        scroll.setVisible(false);
                    }
                }
                catch(IllegalArgumentException er){
                    scroll.setVisible(false);
                    log.setText("Date invalide");
                }
                frame.setContentPane(mainPanel);
            }
        });

        insertc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(selectTab);
                mainPanel.add(selectOptionC);
                mainPanel.add(insertDataAddC);
                mainPanel.add(executeOptionCI);
                log.setText("");
                mainPanel.add(logPanel);
                frame.setContentPane(mainPanel);
            }
        });

        executeCommandCI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int clientId = Integer.parseInt(campIdc.getText());
                    String numeClient = campNumec.getText();
                    String adresaClient = campAdresac.getText();
                    String emailClient = campEmailc.getText();
                    int varstaClient = Integer.parseInt(campVarstac.getText());
                    if(clientId < 1 || varstaClient < 0) {
                        throw new IllegalArgumentException();
                    }
                    if (bank.addPerson(new Person(clientId, numeClient, adresaClient, emailClient, varstaClient))) {
                        log.setText("Operatie incheiata cu succes");
                    } else {
                        log.setText("Operatia nu a putut fi efectuata");
                    }
                }
                catch(IllegalArgumentException er){
                    log.setText("Date invalide");
                }
            }
        });

        stergeIdc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(selectTab);
                mainPanel.add(selectOptionC);
                mainPanel.add(insertIdDelete);
                mainPanel.add(executeOptionCD);
                log.setText("");
                mainPanel.add(logPanel);

                frame.setContentPane(mainPanel);
            }
        });

        executeCommandCD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(selectTab);
                mainPanel.add(selectOptionC);
                mainPanel.add(logPanel);
                try {
                    if(Integer.parseInt(campIdcD.getText()) < 0){
                        throw new IllegalArgumentException();
                    }
                    if (bank.removePerson(Integer.parseInt(campIdcD.getText()))) {
                        log.setText("Operatie incheiata cu succes");
                    } else {
                        log.setText("Operatia nu a putut fi efectuata");
                    }
                }
                catch(IllegalArgumentException er){
                    log.setText("Date invalide");
                }
                frame.setContentPane(mainPanel);
            }
        });

        updateC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(selectTab);
                mainPanel.add(selectOptionC);
                mainPanel.add(insertDataUpdateC);
                mainPanel.add(executeCommandCU);
                log.setText("");
                mainPanel.add(logPanel);

                frame.setContentPane(mainPanel);
            }
        });

        executeCommandCU.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int clientId = Integer.parseInt(campIdcU.getText());
                    String numeClient = campNumecU.getText();
                    String adresaClient = campAdresacU.getText();
                    String emailClient = campEmailcU.getText();
                    int varstaClient = Integer.parseInt(campVarstacU.getText());
                    if(clientId < 1 || varstaClient < 0) {
                        throw new IllegalArgumentException();
                    }
                    if (bank.updatePerson(new Person(clientId, numeClient, adresaClient, emailClient, varstaClient), clientId)) {
                        log.setText("Operatie incheiata cu succes");
                    } else {
                        log.setText("Operatia nu a putut fi efectuata");
                    }
                }
                catch(IllegalArgumentException er){
                    log.setText("Date invalide");
                }
                frame.setContentPane(mainPanel);
            }
        });






        JPanel selectOptionA = new JPanel();
        JPanel executeOptionAW = new JPanel();
        JPanel executeOptionAS = new JPanel();
        JPanel executeOptionAI = new JPanel();
        JPanel executeOptionAD = new JPanel();
        JPanel executeOptionAU = new JPanel();
        JPanel insertDataAddA = new JPanel();
        JPanel insertIdSearchCA = new JPanel();
        JPanel insertIdDeleteA = new JPanel();
        JPanel insertDataUpdateA = new JPanel();

        customerPanel.setPreferredSize(new Dimension(1000,400));
        selectOptionC.setPreferredSize(new Dimension(1000,50));
        insertIdSearchCA.setPreferredSize(new Dimension(280,30));
        insertDataAddA.setPreferredSize(new Dimension(280,100));
        insertDataUpdateC.setPreferredSize(new Dimension(322, 130));
        insertIdDeleteA.setPreferredSize(new Dimension(280, 50));
        insertDataUpdateA.setPreferredSize(new Dimension(280, 100));
        JButton viewAllA = new JButton("Afiseaza conturi client");
        JButton insertA = new JButton("Insereaza cont client");
        JButton stergeIdA = new JButton("Sterge cont client dupa id");
        JButton updateA = new JButton("Actualizeaza cont client dupa id");
        JButton executeCommandAW = new JButton("Executa");
        JButton executeCommandAS = new JButton("Executa");
        JButton executeCommandAI = new JButton("Executa");
        JButton executeCommandAD = new JButton("Executa");
        JButton executeCommandAU = new JButton("Executa");

        selectOptionA.add(viewAllA);
        selectOptionA.add(insertA);
        selectOptionA.add(stergeIdA);
        selectOptionA.add(updateA);

        executeOptionAW.add(executeCommandAW);
        executeOptionAS.add(executeCommandAS);
        executeOptionAI.add(executeCommandAI);
        executeOptionAD.add(executeCommandAD);
        executeOptionAU.add(executeCommandAU);

        JLabel campIdAWL = new JLabel("ID persoana");
        JTextField campIdAW = new JTextField("");
        campIdAW.setPreferredSize(new Dimension(200,20));

        insertIdSearchCA.add(campIdAWL);
        insertIdSearchCA.add(campIdAW);


        JLabel campIdAIL = new JLabel("ID cont");
        JTextField campIdIA = new JTextField("");
        campIdIA.setPreferredSize(new Dimension(200,20));

        JLabel campIdPIL = new JLabel("ID persoana");
        JTextField campIdPIA = new JTextField("");
        campIdPIA.setPreferredSize(new Dimension(200,20));

        String[] accountType = {"Cont economii", "Cont de cumparaturi"};
        JComboBox<String> accType = new JComboBox<>(accountType);
        accType.setPreferredSize(new Dimension(240,20));


        JLabel campInterestAL = new JLabel("Dobanda");
        JTextField campInterestA = new JTextField("");
        campInterestA.setPreferredSize(new Dimension(200,20));

        insertDataAddA.add(campIdAIL);
        insertDataAddA.add(campIdIA);
        insertDataAddA.add(campIdPIL);
        insertDataAddA.add(campIdPIA);
        insertDataAddA.add(accType);
        insertDataAddA.add(campInterestAL);
        insertDataAddA.add(campInterestA);


        JLabel campIdASDL = new JLabel("ID cont");
        JTextField campIdASD = new JTextField("");
        campIdASD.setPreferredSize(new Dimension(200,20));

        JLabel campIdPSDL = new JLabel("ID persoana");
        JTextField campIdPSD = new JTextField("");
        campIdPSD.setPreferredSize(new Dimension(200,20));

        insertIdDeleteA.add(campIdASDL);
        insertIdDeleteA.add(campIdASD);
        insertIdDeleteA.add(campIdPSDL);
        insertIdDeleteA.add(campIdPSD);

        executeOptionAD.add(executeCommandAD);

        JLabel campIdAUL = new JLabel("ID cont");
        JTextField campIdUA = new JTextField("");
        campIdUA.setPreferredSize(new Dimension(200,20));

        JLabel campIdPUL = new JLabel("ID persoana");
        JTextField campIdPUA = new JTextField("");
        campIdPUA.setPreferredSize(new Dimension(200,20));

        JComboBox<String> accTypeUpdate = new JComboBox<>(accountType);
        accTypeUpdate.setPreferredSize(new Dimension(240,20));


        JLabel campInterestAUL = new JLabel("Dobanda");
        JTextField campInterestAU = new JTextField("");
        campInterestAU.setPreferredSize(new Dimension(200,20));

        insertDataUpdateA.add(campIdAUL);
        insertDataUpdateA.add(campIdUA);
        insertDataUpdateA.add(campIdPUL);
        insertDataUpdateA.add(campIdPUA);
        insertDataUpdateA.add(accTypeUpdate);
        insertDataUpdateA.add(campInterestAUL);
        insertDataUpdateA.add(campInterestAU);

        executeOptionAU.add(executeCommandAU);


        account.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(selectTab);
                mainPanel.add(selectOptionA);
                frame.setContentPane(mainPanel);
            }
        });

        viewAllA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(selectTab);
                mainPanel.add(selectOptionA);
                mainPanel.add(insertIdSearchCA);
                mainPanel.add(executeOptionAW);


                frame.setContentPane(mainPanel);
            }
        });

        executeCommandAW.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(selectTab);
                mainPanel.add(selectOptionA);
                mainPanel.add(resultPanel);
                mainPanel.add(logPanel);
                log.setText("");
                try {
                    int idPerson = Integer.parseInt(campIdAW.getText());
                    if(idPerson < 1) {
                        throw new IllegalArgumentException();
                    }
                    createTableAccount(bank.viewAccounts(idPerson), idPerson);
                    if (tableMode != null) {
                        table.setModel(tableMode);
                        scroll.setVisible(true);
                    } else {
                        scroll.setVisible(false);
                    }
                }
                catch(IllegalArgumentException er){
                    scroll.setVisible(false);
                    log.setText("Date invalide");
                }
                frame.setContentPane(mainPanel);
            }
        });

        insertA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(selectTab);
                mainPanel.add(selectOptionA);
                mainPanel.add(insertDataAddA);
                mainPanel.add(executeOptionAI);

                frame.setContentPane(mainPanel);
            }
        });

        executeCommandAI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(selectTab);
                mainPanel.add(selectOptionA);
                log.setText("");
                mainPanel.add(logPanel);
                try {
                    int accountId = Integer.parseInt(campIdIA.getText());
                    int personId = Integer.parseInt(campIdPIA.getText());
                    double interest = Double.parseDouble(campInterestA.getText());
                    if(accountId < 1 || personId < 1 || interest < 0) {
                        throw new IllegalArgumentException();
                    }
                    boolean res;
                    if (accType.getSelectedItem().toString().equals("Cont economii")) {
                        if(interest > 0) {
                            res = bank.addAccount(new SavingAccount(accountId, interest), personId);
                        }
                        else {
                            throw new IllegalArgumentException();
                        }
                    } else {
                        res = bank.addAccount(new SpendingAccount(accountId), personId);
                    }

                    if (res) {
                        log.setText("Operatie incheiata cu succes");
                    } else {
                        log.setText("Operatia nu a putut fi efectuata");
                    }
                }
                catch(IllegalArgumentException er){
                    log.setText("Date invalide");
                }
                frame.setContentPane(mainPanel);
            }
        });

        stergeIdA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(selectTab);
                mainPanel.add(selectOptionA);
                mainPanel.add(insertIdDeleteA);
                mainPanel.add(executeOptionAD);

                frame.setContentPane(mainPanel);
            }
        });

        executeCommandAD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(selectTab);
                mainPanel.add(selectOptionA);
                log.setText("");
                mainPanel.add(logPanel);
                try {
                    int accountId = Integer.parseInt(campIdASD.getText());
                    int personId = Integer.parseInt(campIdPSD.getText());
                    if(accountId < 1 || personId < 1) {
                        throw new IllegalArgumentException();
                    }
                    if (bank.removeAccount(personId, accountId)) {
                        log.setText("Operatie incheiata cu succes");
                    } else {
                        log.setText("Operatia nu a putut fi efectuata");
                    }
                }
                catch(IllegalArgumentException er){
                    log.setText("Date invalide");
                }
                frame.setContentPane(mainPanel);
            }
        });

        updateA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(selectTab);
                mainPanel.add(selectOptionA);
                mainPanel.add(insertDataUpdateA);
                mainPanel.add(executeOptionAU);

                frame.setContentPane(mainPanel);
            }
        });

        executeCommandAU.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(selectTab);
                mainPanel.add(selectOptionA);
                log.setText("");
                mainPanel.add(logPanel);
                try {
                    int accountId = Integer.parseInt(campIdUA.getText());
                    int personId = Integer.parseInt(campIdPUA.getText());
                    if(accountId < 1 || personId < 1) {
                        throw new IllegalArgumentException();
                    }
                    boolean res;
                    if (accTypeUpdate.getSelectedItem().toString().equals("Cont economii")) {
                        Account acc = new SavingAccount(accountId, Double.parseDouble(campInterestAU.getText()));
                        acc.setSum(bank.getAccount(personId, accountId).getSum());
                        res = bank.updateAccount(personId, accountId, acc);
                    } else {
                        Account acc = new SpendingAccount(accountId);
                        acc.setSum(bank.getAccount(personId, accountId).getSum());
                        res = bank.updateAccount(personId, accountId, acc);
                    }
                    if (res) {
                        log.setText("Operatie incheiata cu succes");
                    } else {
                        log.setText("Operatia nu a putut fi efectuata");
                    }
                }
                catch(IllegalArgumentException | NullPointerException er){
                    log.setText("Date invalide");
                }

                frame.setContentPane(mainPanel);
            }
        });


        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    if (table.getSelectedColumn() == 0) {

                        Object o = table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
                        if (o != null) {
                            ArrayList<Person> p = bank.viewPerson(Integer.parseInt(o.toString()));


                            String[] columnName = {"id", "nume", "adresa", "email", "varsta"};
                            Object data[][] = new Object[1][5];
                            data[0][0] = p.get(0).getId();
                            data[0][1] = p.get(0).getName();
                            data[0][2] = p.get(0).getAddress();
                            data[0][3] = p.get(0).getEmail();
                            data[0][4] = p.get(0).getAge();

                            tableModePersoana = new DefaultTableModel(data, columnName);
                            tablePersoana.setModel(tableModePersoana);


                            frameTabel.setVisible(true);
                            frameTabel.setContentPane(panelTabel);

                            scrollPersoana.setVisible(true);
                        }
                    }
                }
            }
        });






        JPanel selectOptionO = new JPanel();
        JPanel insertDeposit = new JPanel();
        JPanel insertWithdraw = new JPanel();
        JPanel executeOptionOW = new JPanel();
        JPanel executeOptionOD = new JPanel();

        selectOptionO.setPreferredSize(new Dimension(1000,50));
        insertDeposit.setPreferredSize(new Dimension(300,75));
        insertWithdraw.setPreferredSize(new Dimension(300,75));

        JButton depositSum = new JButton("Depunere");
        JButton withdrawSum = new JButton("Retragere");
        JButton executeCommandDep = new JButton("Executa");
        JButton executeCommandWit = new JButton("Executa");

        selectOptionO.add(depositSum);
        selectOptionO.add(withdrawSum);

        executeOptionOD.add(executeCommandDep);
        executeOptionOW.add(executeCommandWit);


        JLabel campIdAOL = new JLabel("ID cont");
        JTextField campIdOA = new JTextField("");
        campIdOA.setPreferredSize(new Dimension(200,20));

        JLabel campIdPOL = new JLabel("ID persoana");
        JTextField campIdPOA = new JTextField("");
        campIdPOA.setPreferredSize(new Dimension(200,20));

        JLabel campSumL = new JLabel("Suma");
        JTextField campSum = new JTextField("");
        campSum.setPreferredSize(new Dimension(200,20));

        JLabel campIdAOWL = new JLabel("ID cont");
        JTextField campIdOAW = new JTextField("");
        campIdOAW.setPreferredSize(new Dimension(200,20));

        JLabel campIdPOWL = new JLabel("ID persoana");
        JTextField campIdPOWA = new JTextField("");
        campIdPOWA.setPreferredSize(new Dimension(200,20));

        JLabel campSumWL = new JLabel("Suma");
        JTextField campSumW = new JTextField("");
        campSumW.setPreferredSize(new Dimension(200,20));

        insertDeposit.add(campIdAOL);
        insertDeposit.add(campIdOA);
        insertDeposit.add(campIdPOL);
        insertDeposit.add(campIdPOA);
        insertDeposit.add(campSumL);
        insertDeposit.add(campSum);

        insertWithdraw.add(campIdAOWL);
        insertWithdraw.add(campIdOAW);
        insertWithdraw.add(campIdPOWL);
        insertWithdraw.add(campIdPOWA);
        insertWithdraw.add(campSumWL);
        insertWithdraw.add(campSumW);



        operation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(selectTab);
                mainPanel.add(selectOptionO);
                frame.setContentPane(mainPanel);
            }
        });

        depositSum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(selectTab);
                mainPanel.add(selectOptionO);
                mainPanel.add(insertDeposit);
                mainPanel.add(executeOptionOD);
                frame.setContentPane(mainPanel);
            }
        });

        withdrawSum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(selectTab);
                mainPanel.add(selectOptionO);
                mainPanel.add(insertWithdraw);
                mainPanel.add(executeOptionOW);
                frame.setContentPane(mainPanel);
            }
        });

        executeCommandDep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(selectTab);
                mainPanel.add(selectOptionO);
                log.setText("");
                mainPanel.add(logPanel);
                try {
                    int clientId = Integer.parseInt(campIdPOA.getText());
                    int accountId = Integer.parseInt(campIdOA.getText());
                    double sum = Double.parseDouble(campSum.getText());
                    if(sum > 0 && clientId >= 1 && accountId >= 1) {
                        if (bank.deposit(clientId, accountId, sum)) {
                            log.setText("Operatie incheiata cu succes");
                        } else {
                            log.setText("Operatia nu a putut fi efectuata");
                        }
                    }
                    else {
                        log.setText("Date invalide");
                    }
                }
                catch(IllegalArgumentException er){
                    log.setText("Date invalide");
                }
                frame.setContentPane(mainPanel);

            }
        });


        executeCommandWit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(selectTab);
                mainPanel.add(selectOptionO);
                log.setText("");
                mainPanel.add(logPanel);
                int clientId = Integer.parseInt(campIdPOWA.getText());
                int accountId = Integer.parseInt(campIdOAW.getText());
                double sum = Double.parseDouble(campSumW.getText());
                if(sum > 0 && clientId >= 1 && accountId >= 1) {
                    if (bank.withdraw(clientId, accountId, sum)) {
                        log.setText("Operatie incheiata cu succes");
                    } else {
                        log.setText("Operatia nu a putut fi efectuata");
                    }
                }
                else {
                    log.setText("Date invalide");
                }
                frame.setContentPane(mainPanel);
            }
        });


        mainPanel.add(selectTab);

        frame.add(mainPanel);
        frame.setVisible(true);
        frame.setContentPane(mainPanel);


        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    bank.closeSerialize();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });


    }







    @SuppressWarnings("unchecked")
    private void createTablePerson(ArrayList<Person> person){
        String[] columnName = {"id", "nume", "adresa", "email", "varsta"};
        Object data[][] = new Object[100][5];
        int row = 0;
        boolean sem = false;
        for(Person p: person) {
            sem = true;
            data[row][0] = p.getId();
            data[row][1] = p.getName();
            data[row][2] = p.getAddress();
            data[row][3] = p.getEmail();
            data[row][4] = p.getAge();
            row++;
        }
        if(sem) {
            tableMode = new DefaultTableModel(data, columnName);
        }
        else {
            tableMode = null;
        }
    }

    private void createTableAccount(ArrayList<Account> accounts, int id) {
        String[] columnName = {"ID Persoana", "ID Cont", "Suma disponibila", "Tipul contului", "Dobanda"};
        Object data[][] = new Object[100][5];
        int row = 0;
        if(!accounts.isEmpty()) {
            for (Account a : accounts) {
                data[row][0] = id;
                data[row][1] = a.getId();
                data[row][2] = a.getSum();
                if (a instanceof SpendingAccount) {
                    data[row][3] = "Cont de cumparaturi";
                } else {
                    data[row][3] = "Cont de economii";
                    data[row][4] = ((SavingAccount) a).getInterest();
                }
                row++;
            }
            tableMode = new DefaultTableModel(data, columnName);
        }
        else {
            tableMode = null;
        }
    }








    public static void main(String[] args) throws IOException, ClassNotFoundException {
/*
        Bank bank = new Bank();
        //bank.addPerson(null);
        bank.addPerson(new Person(1, "Marginean Gheorghe", "Strdada Mehedinti nr.37", "mg@gmail.com", 44));
        //bank.addPerson(new Person(1, "Marginean Gheorghe", "Strdada Mehedinti nr.37", "mg@gmail.com", 44));
        bank.addAccount(new SpendingAccount(1), 1);
        System.out.println(bank.addAccount(new SavingAccount(2, 2.11), 1));
       //bank.closeSerialize();
    }

*/

        Thread tt = new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    SwingUtilities.invokeAndWait(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            EventQueue.invokeLater(new Runnable() { public void run() {
                                GUI gui = null;
                                try {
                                    gui = new GUI();
                                } catch (IOException | ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                                gui.userInterface();

                            }});
                        }
                    });
                } catch (InvocationTargetException | InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });
        tt.start();
    }


}
