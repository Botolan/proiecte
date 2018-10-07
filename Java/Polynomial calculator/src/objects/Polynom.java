package objects;

import java.util.ArrayList;
import java.util.Collections;

public class Polynom {
    private ArrayList<Monom> monoms;


    public Polynom(ArrayList<Monom> monoms){
        this.monoms = monoms;
    }

    public ArrayList<Monom> getMonomList() {
        return this.monoms;
    }

    public String toString(){
        if(!monoms.isEmpty()) {
            String s = new String("");
            for (Monom i : monoms) {
                s = s + i.printMonom(monoms.get(0));
            }
            return s;
        }
        return "0";
    }

    public void addition(Polynom p) {
        ArrayList<Monom> removeMonom = new ArrayList<>();//se creaza un arraylist pentru a sterge toate monoamele care au coeficientul 0 in urma operatiei
        for(Monom i: p.getMonomList()) {//pentru fiecare monom din polinomul p
            boolean flag = false;
            for(Monom j: monoms) {
                if(i.getPower() == j.getPower()) {//daca se gasesc 2 monoame cu acelasi exponent
                    flag = true;
                    j.setCoefficient(i.getCoefficient() + j.getCoefficient());//adunam coeficientii
                    if(j.getCoefficient() == 0.0) {//daca coeficientul e 0, adaugam monomul in lista de stergere
                        removeMonom.add(j);
                    }
                }
            }
            if(!flag) {//daca flag e false adaugam monomul in lista
                monoms.add(new Monom(i.getPower(),i.getCoefficient()));
            }
        }
        Collections.sort(monoms);//sortam monoamele
        monoms.removeAll(removeMonom);//stergem toate monoamele din lista cu coeficientul 0
    }

    public void substraction(Polynom p) {
        p.negativeMultiply();//negam toti coeficientii polinomului p
        this.addition(p);//apelam operatia de adunare
    }


    private void negativeMultiply(){
        for(Monom i: monoms) {
            i.setCoefficient(i.getCoefficient() * (-1));//inmultim fiecare coeficient cu -1
        }
    }


    public void multiply(Polynom p) {
        ArrayList<Monom> result = new ArrayList<>();//monom de resultat
        ArrayList<Monom> remove = new ArrayList<>();//monom de stergere
        Monom m = new Monom(0,0);
        for(Monom i: monoms) {
            for(Monom j: p.getMonomList()) {//parcurgem elementele celor 2 lista monoms si p
                boolean flag = false;
                m.setCoefficient(i.getCoefficient() * j.getCoefficient());//inmultim coeficientii
                m.setPower(i.getPower() + j.getPower());//adunam exponentii
                for(Monom k: result) {//parcurgem lista results, daca monomul m si k au acelasi exponent adunam coeficientii
                    if(k.getPower() == m.getPower()) {
                        k.setCoefficient(k.getCoefficient() + m.getCoefficient());
                        flag = true;
                    }
                }
                if(!flag) {//daca nu exista exponentul in lista results adaugam un monom nou in lista result
                    result.add(new Monom(m.getPower(), m.getCoefficient()));
                }
            }
        }
        for(Monom i: result) {//adaugam in remove toate monomaele cu coeficietii 0
            if(i.getCoefficient() == 0.0) {
                remove.add(i);
            }
        }
        result.removeAll(remove);
        Collections.sort(result);
        this.monoms.clear();//stergem continutul lista monoms
        this.monoms.addAll(result);//adaugam in monoms elementele liste result
    }


    public void derivate(){
        for(Monom i: monoms) {
            i.setCoefficient(i.getCoefficient() * i.getPower());
            i.setPower(i.getPower() - 1);
        }
        //verificam daca ultimul element al listei are exponentul mai mic ca 0, daca da il stergem din lista
        if ((!monoms.isEmpty()) && (monoms.get(monoms.size() - 1).getPower() == -1)){
            monoms.remove(monoms.get(monoms.size() - 1));
        }
    }

    public void integrate(){
        for(Monom i: monoms) {
            i.setCoefficient(i.getCoefficient()/(i.getPower() + 1));
            i.setPower(i.getPower() + 1);
        }
    }
}
