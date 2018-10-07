package objects;


import java.text.DecimalFormat;

public class Monom implements Comparable<Monom>{
    private int power;
    private double coefficient;

    public Monom(int power, double coefficient){
        this.power = power;
        this.coefficient = coefficient;
    }

    public double getCoefficient(){
        return this.coefficient;
    }

    public int getPower(){
        return this.power;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String printMonom(Monom monom){
        boolean first = false;
        DecimalFormat df = new DecimalFormat("#.##");
        if((monom.getCoefficient() == this.coefficient) && (monom.getPower() == this.power)){
            first = true;//verificam daca e primul, daca este in loc de semnnul + va fi sirul vid
        }
        if(power != 0) {//returnam cu x^ daca puterea diferita de 0
            if(coefficient > 0) {//folosim Math.round() pentru a rotunji coeficientul la 2 cifre zecimale
                return (first?"":"+") + (coefficient == 1?"":df.format(coefficient)) + (power == 1?"x":"x^" + power);
            }
            else {
                return (coefficient == -1?"-":df.format(coefficient)) + (power == 1?"x":"x^" + power);
            }
        }//returnam fara x^
        else if(coefficient > 0){
            return (first?"":"+") + df.format(coefficient);
        }
        return "" + coefficient;
    }

    @Override
    public int compareTo(Monom o) {//sortam sirul in functie de exponent
        return (o.getPower() - this.power);
    }
}
