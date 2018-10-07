package objects;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

public class Person implements Serializable, Observer {
    private int id;
    private String name;
    private String address;
    private String email;
    private int age;

    public Person(int id){
        this.id = id;
    }


    public Person(int id, String name, String address, String email, int age) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String getAddress() {
        return address;
    }


    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge(){
        return age;
    }

    @Override
    public String toString(){
        return "ID: " + id + " Nume: " + name + " Adresa: " + address + " Email: " + email + " Varsta: " + age;
    }

    @Override
    public int hashCode(){
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Person)obj).getId() == this.id;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Pentru persoana " + id + " " + arg);
    }
}
