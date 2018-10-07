package model;

public class OrderDate {
    private int id;
    private int day;
    private int month;
    private int year;
    private int clientId;

    public OrderDate() {

    }


    public OrderDate(int day, int month, int year, int clientId) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.clientId = clientId;
    }

    public OrderDate(int id, int day, int month, int year, int clientId) {
        this.id = id;
        this.day = day;
        this.month = month;
        this.year = year;
        this.clientId = clientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String toString() {
        return "OrderDate [id=" + id + ", day=" + day + ", month=" + month + " year=" + year + " clientId=" + clientId + "]\n";
    }
}
