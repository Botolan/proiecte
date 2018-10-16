import java.awt.*;
import java.util.*;


public abstract class Forma {
    private Patrat[] forma = new Patrat[4];
    private static Color[] culori = {Color.orange, Color.red, Color.green, Color.blue, Color.magenta};

    public Forma(Patrat[] forma) {
        this.forma = forma;
    }
    public Patrat getPatratIndex(int i) {
        return forma[i];
    }
    private int validareMiscare(int directie, ZonaJoc zona) {
        for (int i = 0; i < 4; i++) {
            if (directie == 0) {
                if (((forma[i].getPosX() - 1 > -1) && zona.getOcupat(forma[i].getPosY(), forma[i].getPosX() - 1) != null) || (forma[i].getPosX() - 1 < 0)) {
                    return -1;
                }
            } else if (directie == 1) {
                if ((forma[i].getPosY() + 1 < 25) && zona.getOcupat(forma[i].getPosY() + 1, forma[i].getPosX()) != null) {
                    return 0;
                } else if (forma[i].getPosY() + 1 > 24) {
                    return 0;
                }
            } else if (directie == 2) {
                if (((forma[i].getPosX() + 1 < 10) && zona.getOcupat(forma[i].getPosY(), forma[i].getPosX() + 1) != null) || (forma[i].getPosX() + 1 > 9)) {
                    return -1;
                }
            }
        }
        if ((directie != -1) && (directie != 5)) {
            return 1;
        } else {
            return -1;
        }
    }
    public boolean miscareFormaPrincipala(int directie, ZonaJoc zona) {
        int rezultat = validareMiscare(directie, zona);
        if (rezultat == 1) {
            for (int i = 0; i < 4; i++) {
                if (directie == 0) {
                    forma[i].setPosX(forma[i].getPosX() - 1);
                } else {
                    forma[i].setPosX(forma[i].getPosX() + 1);
                }
            }
            return false;
        } else if (rezultat == 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean miscareFormaPrincipalaJos(ZonaJoc zona){
        int rezultat = validareMiscare(1, zona);
        if (rezultat == 1) {
            for (int i = 0; i < 4; i++) {
                forma[i].setPosY(forma[i].getPosY() + 1);
            }
            return false;
        } else if (rezultat == 0) {
            return true;
        } else {
            return false;
        }
    }
    public static Forma generareForma() {
        Random numarRandom = new Random();
        int randomInt = numarRandom.nextInt(7);
        switch (randomInt) {
            case 0: {
                return FormaI.generareFormaI(culori[numarRandom.nextInt(culori.length)]);
            }
            case 1: {
                return FormaJ.generareFormaJ(culori[numarRandom.nextInt(culori.length)]);
            }
            case 2: {
                return FormaL.generareFormaL(culori[numarRandom.nextInt(culori.length)]);
            }
            case 3: {
                return FormaO.generareFormaO(culori[numarRandom.nextInt(culori.length)]);
            }
            case 4: {
                return FormaS.generareFormaS(culori[numarRandom.nextInt(culori.length)]);
            }
            case 5: {
                return FormaT.generareFormaT(culori[numarRandom.nextInt(culori.length)]);
            }
            case 6: {
                return FormaZ.generareFormaZ(culori[numarRandom.nextInt(culori.length)]);
            }
        }
        return null;
    }
    private int [][] matriceValori(Forma formaAux,int directie) {
        int valori[][] = new int[4][2];
        int pivot = -1;
        if(formaAux instanceof FormaI){
            pivot = FormaI.getPivot();
        }
        else if(formaAux instanceof FormaJ){
            pivot = FormaJ.getPivot();
        }
        else if(formaAux instanceof FormaL){
            pivot = FormaL.getPivot();
        }
        else if(formaAux instanceof FormaS){
            pivot = FormaS.getPivot();
        }
        else if(formaAux instanceof FormaT){
            pivot = FormaT.getPivot();
        }
        else if(formaAux instanceof FormaZ){
            pivot = FormaZ.getPivot();
        }
        if(pivot != -1) {
            for (int i = 0; i < 4; i++) {
                int rx = formaAux.getPatratIndex(i).getPosX() - formaAux.getPatratIndex(pivot).getPosX();
                int ry = formaAux.getPatratIndex(i).getPosY() - formaAux.getPatratIndex(pivot).getPosY();
                if (directie == 3) {
                    ry *= -1;
                } else {
                    rx *= -1;
                }
                valori[i][0] = formaAux.getPatratIndex(pivot).getPosX() + ry;
                valori[i][1] = formaAux.getPatratIndex(pivot).getPosY() + rx;
            }
            return valori;
        }
        else {
            return null;
        }
    }
    private boolean validareValori(int[][] valori, ZonaJoc zona) {
        for(int i = 0;i < 4;i++) {
            if (!((valori[i][1] < 25) && (valori[i][1] > -1) && (valori[i][0] > -1) && (valori[i][0] < 10) && (zona.getOcupat(valori[i][1], valori[i][0]) == null))) {
                return false;
            }
        }
        return true;
    }
    private void setareValori(int[][] valori){
        for(int i = 0;i < 4;i++){
            forma[i].setPosX(valori[i][0]);
            forma[i].setPosY(valori[i][1]);
        }
    }
    public void rotireForma(ZonaJoc zona, Forma formaAux, int directie) {
        int valori[][] = matriceValori(formaAux, directie);
        if(valori != null) {
            if (validareValori(valori, zona)) {
                setareValori(valori);
            }
        }
    }
    public static int getIndexForma(Forma forma){
        if(forma instanceof FormaI){
            return 0;
        }
        else if(forma instanceof FormaJ){
            return 1;
        }
        else if(forma instanceof FormaL){
            return 2;
        }
        else if(forma instanceof FormaO){
            return 3;
        }
        else if(forma instanceof FormaZ){
            return 4;
        }
        else if(forma instanceof FormaS){
            return 5;
        }
        else if(forma instanceof FormaT){
            return 6;
        }
        return -1;
    }

    public boolean gameOver(ZonaJoc zonajoc){
        for(int i = 0;i < 4;i++){
            if(zonajoc.getOcupat(forma[i].getPosY(),forma[i].getPosX()) != null){
                return true;
            }
        }
        return false;
    }

}
