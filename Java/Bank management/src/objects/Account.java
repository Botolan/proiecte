package objects;


import java.util.Observable;
import java.io.Serializable;

public abstract class Account extends Observable implements Serializable {
    private int id;
    private double sum;

    public void setId(int id) {
        this.id = id;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    Account(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public double getSum(){
        return sum;
    }

    public void withdrawSum(double sum) {
        this.sum -= sum;
        if (this.sum < 0) {
            this.sum = 0;
        }
        setChanged();
        notifyObservers("din contul " + id + " a fost extrasa suma de " + sum);
    }

    public void depositSum(double sum) {
        this.sum += sum;
        setChanged();
        notifyObservers("in contul " + id + " a fost depusa suma de " + sum);
    }
}
