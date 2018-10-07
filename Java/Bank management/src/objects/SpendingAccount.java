package objects;

public class SpendingAccount extends Account {

    public SpendingAccount(int id) {
        super(id);
    }

    public void setSum(double sum) {
        super.setSum(sum);
    }

    public void setId(int id) {
        super.setId(id);
    }

    public int getId(){
        return super.getId();
    }

    public double getSum(){
        return super.getSum();
    }

    public String toString(){
        return "Cont de cheltuieli -> ID: " + super.getId() + " Sum: " + super.getSum();
    }

}