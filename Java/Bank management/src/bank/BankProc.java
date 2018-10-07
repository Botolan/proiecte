package bank;

import objects.Account;
import objects.Person;

import java.util.List;

public interface BankProc {


    /**
     * insereaza o noua persoana in hashtable
     * @pre person != null
     * @post result == true
     */

    public boolean addPerson(Person person);

    public boolean removePerson(int id);

    public List<Person> viewPerson(int id);

    public boolean updatePerson(Person person, int id);

    public List<Person> viewPersons();

    public boolean addAccount(Account account, int personId);

    public List<Account> viewAccounts(int personId);

    public Account getAccount(int personId, int accountId);

    public boolean removeAccount(int personId, int accountId);

    public boolean updateAccount(int personId, int accountId, Account account);

}
