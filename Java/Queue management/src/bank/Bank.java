package bank;

import objects.Account;
import objects.Person;
import objects.SavingAccount;
import objects.SpendingAccount;

import java.io.*;
import java.util.*;

/**
 * @invariant sum > 0 && accountId >=1 && personId >= 1 && wellFormed()
 */

public class Bank implements BankProc, Serializable{
    private Hashtable<Person, List<Account>> hashTable;

    public Bank() throws IOException, ClassNotFoundException {
       // hashTable = new Hashtable<>();

        hashTable = (Hashtable)deserialize();
        Enumeration e = hashTable.keys();
        while(e.hasMoreElements()){
            Person p = (Person)e.nextElement();
            for(Account acc : hashTable.get(p)){
                acc.addObserver(p);
            }
        }
    }

    /**
     * @pre wellFormed()
     * @pre person != null
     * @post wellFormed()
     */


    @Override
    public boolean addPerson(Person person) {
        assert (wellFormed());
        assert (person != null);
        List<Account> account = new ArrayList<>();
        if(hashTable.containsKey(person)){
            assert (wellFormed());
            return false;
        }
        hashTable.put(person, account);
        assert (wellFormed());
        return true;
    }

    /**
     * @pre wellFormed()
     * @pre person != null
     * @pre id >= 1
     * @post wellFormed()
     */

    @Override
    public boolean updatePerson(Person person, int id) {
        assert (wellFormed());
        assert (person != null);
        assert (id >= 1);
        Person personCurrent = new Person(id);
        Enumeration e = hashTable.keys();
        while(e.hasMoreElements()){
            Person p = (Person)e.nextElement();
            if(p.equals(personCurrent)) {
                List<Account> accounts = hashTable.get(personCurrent);
                hashTable.remove(person);
                hashTable.put(person, accounts);
                assert(wellFormed());
                return true;
            }
        }
        assert(wellFormed());
        return false;
    }

    /**
     * @pre wellFormed()
     * @pre id >= 1
     * @post wellFormed()
     */

    @Override
    public boolean removePerson(int id) {
        assert(wellFormed());
        assert(id >= 1);
        Person person = new Person(id);
        Enumeration e = hashTable.keys();
        while(e.hasMoreElements()){
            Person p = (Person)e.nextElement();
            if(p.equals(person)) {
                hashTable.remove(p);
                assert(wellFormed());
                return true;
            }
        }
        assert(wellFormed());
        return false;
    }

    /**
     * @pre wellFormed()
     * @pre id >= 1
     * @post wellFormed()
     */

    public ArrayList<Person> viewPerson(int id){
        assert(wellFormed());
        assert(id >= 1);
        Person person = new Person(id);
        Enumeration e = hashTable.keys();
        ArrayList<Person> list = new ArrayList<>();
        while(e.hasMoreElements()){
            Person p = (Person)e.nextElement();
            if(p.equals(person)) {
                list.add(p);
                return list;
            }
        }
        assert(wellFormed());
        return list;
    }

    @Override
    public ArrayList<Person> viewPersons() {
        ArrayList<Person> list = new ArrayList<>();
        Enumeration e = hashTable.keys();
        while(e.hasMoreElements()){
            list.add((Person)e.nextElement());
        }
        return list;
    }

    /**
     * @pre wellFormed()
     * @pre account != null
     * @pre personId >= 1
     * @post wellFormed()
     */

    @Override
    public boolean addAccount(Account account, int personId) {
        assert(wellFormed());
        assert(account != null);
        assert(personId >= 1);
        Enumeration e = hashTable.keys();
        Person p = new Person(personId);
        while(e.hasMoreElements()) {
            Person q = (Person)e.nextElement();
            if(p.equals(q)) {
                for(Account acc : hashTable.get(p)) {
                    if(acc.getId() == account.getId()) {
                        assert(wellFormed());
                        return false;
                    }
                }
                account.addObserver(p);
                hashTable.get(p).add(account);
                assert(wellFormed());
                return true;
            }
        }
        assert(wellFormed());
        return false;
    }

    /**
     * @pre wellFormed()
     * @pre personId >= 1
     * @post wellFormed()
     */

    public ArrayList<Account> viewAccounts(int personId) {
        assert(wellFormed());
        assert(personId >= 1);
        Enumeration e = hashTable.keys();
        Person p = new Person(personId);
        ArrayList<Account> list = new ArrayList<>();
        while(e.hasMoreElements()) {
            Person q = (Person)e.nextElement();
            if(p.equals(q)) {
                list.addAll(hashTable.get(q));
                return list;
            }
        }
        assert(wellFormed());
        return list;
    }

    /**
     * @pre wellFormed()
     * @pre personId >= 1
     * @pre accountId >= 1
     * @post wellFormed()
     */

    @Override
    public boolean removeAccount(int personId, int accountId) {
        assert(personId >= 1);
        assert(accountId >= 1);
        assert(wellFormed());
        Enumeration e = hashTable.keys();
        Person p = new Person(personId);
        while(e.hasMoreElements()) {
            Person q = (Person)e.nextElement();
            if(p.equals(q)) {
                for(Account acc : hashTable.get(p)) {
                    if(acc.getId() == accountId) {
                        hashTable.get(p).remove(acc);
                        assert(wellFormed());
                        return true;
                    }
                }
            }
        }
        assert(wellFormed());
        return false;
    }

    /**
     * @pre wellFormed()
     * @pre accountId >= 1
     * @pre personId >= 1
     * @post wellFormed()
     */

    @Override
    public Account getAccount(int personId, int accountId){
        assert(personId >= 1);
        assert(accountId >= 1);
        Enumeration e = hashTable.keys();
        Person p = new Person(personId);
        while(e.hasMoreElements()) {
            Person q = (Person)e.nextElement();
            if(p.equals(q)) {
                for(Account acc : hashTable.get(p)) {
                    if(acc.getId() == accountId) {
                        return acc;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @pre wellFormed()
     * @pre account != null
     * @pre accountId >= 1
     * @pre personId >= 1
     * @post wellFormed()
     */

    @Override
    public boolean updateAccount(int personId, int accountId, Account account) {
        assert(personId >= 1);
        assert(accountId >= 1);
        assert(account != null);
        Enumeration e = hashTable.keys();
        Person p = new Person(personId);
        while(e.hasMoreElements()) {
            Person q = (Person)e.nextElement();
            if(p.equals(q)) {
                for(Account acc : hashTable.get(p)) {
                    if(acc.getId() == accountId) {
                        hashTable.get(p).remove(acc);
                        hashTable.get(p).add(account);
                        account.addObserver(p);
                        assert(wellFormed());
                        return true;
                    }
                }
            }
        }
        assert(wellFormed());
        return false;
    }

    /**
     * @pre wellFormed()
     * @pre sum > 0
     * @pre accountId >= 1
     * @pre personId >= 1
     * @post wellFormed()
     */

    public boolean deposit(int personId, int accountId, double sum){
        assert(personId >= 1);
        assert(accountId >= 1);
        assert(sum > 0);
        assert(wellFormed());
        Enumeration e = hashTable.keys();
        Person p = new Person(personId);
        while(e.hasMoreElements()) {
            Person q = (Person)e.nextElement();
            if(p.equals(q)) {
                for(Account acc : hashTable.get(p)) {
                    if(acc.getId() == accountId) {
                        if(acc instanceof SpendingAccount || (acc instanceof SavingAccount && acc.getSum() == 0)) {
                            acc.depositSum(sum);
                            assert(wellFormed());
                            return true;
                        }
                        else {
                            assert(wellFormed());
                            return false;
                        }
                    }
                }
            }
        }
        assert(wellFormed());
        return false;
    }

    /**
     * @pre wellFormed()
     * @pre sum > 0
     * @pre accountId >= 1
     * @pre personId >= 1
     * @post wellFormed()
     */

    public boolean withdraw(int personId, int accountId, double sum){
        assert(personId >= 1);
        assert(accountId >= 1);
        assert(sum > 0);
        assert(wellFormed());
        Enumeration e = hashTable.keys();
        Person p = new Person(personId);
        while(e.hasMoreElements()) {
            Person q = (Person)e.nextElement();
            if(p.equals(q)) {
                for(Account acc : hashTable.get(p)) {
                    if(acc.getId() == accountId) {
                        if(acc instanceof SpendingAccount && acc.getSum() - sum >= 0){
                            acc.withdrawSum(sum);
                            assert(wellFormed());
                            return true;
                        }
                        else if(acc instanceof SavingAccount && acc.getSum() != 0 && acc.getSum() == sum) {
                            SavingAccount a = ((SavingAccount)acc);
                            a.withdrawSum(a.getSum() + a.getSum() * a.getInterest());
                            assert(wellFormed());
                            return true;
                        }
                        assert(wellFormed());
                        return false;
                    }
                }
            }
        }
        assert(wellFormed());
        return false;
    }

    private static Object deserialize() throws IOException, ClassNotFoundException {
        FileInputStream fileInput = new FileInputStream("Banca");
        ObjectInputStream inputStream = new ObjectInputStream(fileInput);


        Object object = inputStream.readObject();

        inputStream.close();
        fileInput.close();

        return object;
    }

    private static void serialize(Object object) throws IOException {
        FileOutputStream fileOutput = new FileOutputStream("Banca");
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutput);

        outputStream.writeObject(object);

        outputStream.close();
        fileOutput.close();
    }

    public void closeSerialize() throws IOException {
        serialize(hashTable);
    }

    private boolean wellFormed() {
        Enumeration e = hashTable.keys();
        while (e.hasMoreElements()) {
            Person q = (Person) e.nextElement();
            for(Account acc:hashTable.get(q)) {
                if(acc instanceof SavingAccount) {
                    if(acc.getSum() < 0 || ((SavingAccount)acc).getInterest() < 0){
                        return false;
                    }
                }
                else if(acc instanceof SpendingAccount) {
                    if(acc.getSum() < 0) {
                        return false;
                    }
                }
                else {
                    return false;
                }
            }
        }
        return true;
    }
}
