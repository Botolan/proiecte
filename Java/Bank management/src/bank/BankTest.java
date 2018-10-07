package bank;

import objects.Account;
import objects.Person;
import objects.SavingAccount;
import objects.SpendingAccount;

import java.io.IOException;
import java.util.Enumeration;

public class BankTest {

    @org.junit.Test
    public void addPerson() throws IOException, ClassNotFoundException {
        Bank bank = new Bank();
        assert(bank.addPerson(new Person(3, "p1", "a1", "e1", 11)));
        assert(!bank.addPerson(new Person(3, "p1", "a1", "e1", 11)));
    }

    @org.junit.Test
    public void updatePerson() throws IOException, ClassNotFoundException {
        Bank bank = new Bank();
        bank.addPerson(new Person(3, "p1", "a1", "e1", 11));
        assert(bank.updatePerson(new Person(3, "p1", "aasd1", "e1", 11), 3));
        assert(!bank.updatePerson(new Person(3, "p1", "a1", "e1", 11), 4));
    }

    @org.junit.Test
    public void removePerson() throws IOException, ClassNotFoundException {
        Bank bank = new Bank();
        bank.addPerson(new Person(3, "p1", "a1", "e1", 11));
        assert(bank.removePerson(3));
        assert(!bank.removePerson(4));
    }

    @org.junit.Test
    public void addAccount() throws IOException, ClassNotFoundException {
        Bank bank = new Bank();
        bank.addPerson(new Person(3, "p1", "a1", "e1", 11));
        assert(bank.addAccount(new SpendingAccount(1), 3));
        assert(!bank.addAccount(new SavingAccount(1, 1.22), 3));
        assert(bank.addAccount(new SpendingAccount(2), 3));
    }

    @org.junit.Test
    public void removeAccount() throws IOException, ClassNotFoundException {
        Bank bank = new Bank();
        bank.addPerson(new Person(3, "p1", "a1", "e1", 11));
        assert(bank.addAccount(new SpendingAccount(1), 3));
        assert(bank.removeAccount(3, 1));
        assert(!bank.removeAccount(3, 1));
    }

    @org.junit.Test
    public void updateAccount() throws IOException, ClassNotFoundException {
        Bank bank = new Bank();
        bank.addPerson(new Person(3, "p1", "a1", "e1", 11));
        assert(bank.addAccount(new SpendingAccount(1), 3));
        assert(bank.updateAccount(3, 1, new SavingAccount(1, 1.22)));
        assert(!bank.updateAccount(3, 4, new SavingAccount(1, 1.22)));
    }

    @org.junit.Test
    public void withdrawDeposit() throws IOException, ClassNotFoundException {
        Bank bank = new Bank();
        bank.addPerson(new Person(3, "p1", "a1", "e1", 11));
        bank.addAccount(new SpendingAccount(1), 3);
        assert(bank.deposit(3, 1, 100));
        assert(bank.withdraw(3, 1, 100));
    }
}
