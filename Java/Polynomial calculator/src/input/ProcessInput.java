package input;

import objects.Monom;
import objects.Polynom;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessInput {

    public static Polynom processInput(String input) {
        ArrayList<Monom> polynom = new ArrayList<>();
        Pattern goodInput = Pattern.compile("(((([-+])|^)?(\\d*))?(([x])((\\^)([1-9][0-9]*))?)?)");//sir pentru extras monoame
        Matcher getMonoms = goodInput.matcher(input);
        if(wrongInput(input)){//verificam daca expresia input este valida sau nu, in caz afirmativ returnam null
            return null;
        }
        while (getMonoms.find()) {//gasim toate monoamele
            if (getMonoms.group().length() != 0) {
                Monom m = createMonom(getMonoms);//formam monomul
                int index = samePower(m,polynom);//verificam daca monomul are acelasi exponent cu unul deja introdus
                if(index != -1) {//-1 daca nu exista, daca exista adunam coeficientii, la elementul cu indexul "index"
                    polynom.get(index).setCoefficient(polynom.get(index).getCoefficient() + m.getCoefficient());
                }
                else if(m.getCoefficient() != 0){
                    polynom.add(m);
                }
                else {
                    return null;
                }
            }
        }
        return new Polynom(polynom);
    }

    private static boolean wrongInput(String input) {
        Pattern illegalExpression = Pattern.compile("(\\d[x]\\d|[-+]$|[-+]?\\d*[x][x]|[-+]{2,}|[x]\\^?\\d*[x])");//sir cu expresii ilegale
        Matcher regexMatcher = illegalExpression.matcher(input);
        if(regexMatcher.find()) {//daca exista returnam ca este gresit, altfel verificam lungimea grupurilor bune sa fie egala cu lungimea sirului
            return true;
        }
        else {
            return checkLength(input);
        }
    }

    private static boolean checkLength(String input){
        Pattern expression = Pattern.compile("((([-+])|^)?(\\d*))?(([x])((\\^)([1-9][0-9]*))?)?");
        Matcher regexMatcher = expression.matcher(input);
        int length = 0;
        while (regexMatcher.find()) {
            if (regexMatcher.group().length() != 0) {
                length += regexMatcher.group().length();//adunam lungimea grupurilor gasite
            }
        }
        if((length != input.length()) || (input.length() == 0)) {//daca lungimea sirului e diferita de lungimea totala a
            return true;//grupurilor gasite sau lungimea sirului e 0
        }
        return false;

    }

    private static int samePower(Monom m, ArrayList<Monom> list){
        for(Monom i: list) {
            if(m.getPower() == i.getPower()){
                return list.indexOf(i);//returneaza pozitia monomului din lista list pentru care exponentul este egal cu
            }//cel al monomului "m"
        }
        return -1;
    }

    private static Monom createMonom(Matcher regexMatcher) {
        double coefficient = 1;
        int power = 0;
        if(regexMatcher.group(3) != null) {//avem semn sau inceput de linie
            if((regexMatcher.group(4) != null) && (regexMatcher.group(4).equals("-"))){//daca semnul e -
                coefficient = -1;
            }
            if((regexMatcher.group(5) != null) && (!regexMatcher.group(5).isEmpty())) {//daca avem coeficient
                coefficient *= Double.parseDouble(regexMatcher.group(5));
            }
        }
        if(regexMatcher.group(7) != null) {//avem cel putin exponentul 1
            power = 1;
            if (regexMatcher.group(9) != null) {//avem un exponent => 1
                power = Integer.parseInt(regexMatcher.group(10));
            }
        }
        return new Monom(power, coefficient);
    }
}
