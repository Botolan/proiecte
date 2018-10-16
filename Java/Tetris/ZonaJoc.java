public class ZonaJoc {
    private Patrat [][]Matrice = new Patrat[25][10];

    public ZonaJoc(){
        for(int i = 0;i < 25;i++){
            for(int j = 0;j < 10;j++){
                Matrice[i][j] = null;
            }
        }

    }

    public void adaugarePieseZona(Forma forma){
        for(int i = 0;i < 4;i++){
            Matrice[forma.getPatratIndex(i).getPosY()][forma.getPatratIndex(i).getPosX()] = forma.getPatratIndex(i);
        }
    }

    public boolean pregatireEliminareLinii(){
        boolean eliminare = false;
        for(int i = 24; i >= 0; i--){
            boolean liniePlina = true;
            for(int j = 0;j < 10;j++){
                if(Matrice[i][j] == null){
                    liniePlina = false;
                }
            }
            if(liniePlina){
                for(int j = 0;j < 10;j++){
                    Matrice[i][j].setPatratEliminare();
                }
                eliminare = true;
            }
        }
        return eliminare;
    }

    public int eliminareLinii(){
        int liniiPline = 0;
        for(int i = 24; i >= 0; i--){
            boolean liniePlina = true;
            for(int j = 0;j < 10;j++){
                if(Matrice[i][j] == null){
                    liniePlina = false;
                }
            }
            if(liniePlina){
                liniiPline++;
                for(int k = i; k > 1;k--){
                    for(int j = 0;j < 10;j++){
                        Matrice[k][j] = Matrice[k - 1][j];
                    }
                }
                i++;
            }
        }

        return liniiPline;
    }


    public Patrat getOcupat(int j, int i) {
        return Matrice[j][i];
    }

    public void resetZonaJoc(){
        for(int i = 0;i < 25;i++){
            for(int j = 0;j < 10;j++){
                Matrice[i][j] = null;
            }
        }
    }
}


