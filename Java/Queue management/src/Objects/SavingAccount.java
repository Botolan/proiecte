package objects;

public class SavingAccount extends Account {
    private double interest;

    public SavingAccount(int id, double interest) {
        super(id);
        this.interest = interest;
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

    public double getInterest(){
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public String toString(){
        return "Cont de cheltuieli -> ID: " + super.getId() + " Sum: " + super.getSum() + " interest "  + interest;
    }

}
