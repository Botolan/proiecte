import java.awt.*;
import java.awt.image.BufferStrategy;


public class Joc extends Canvas implements Runnable {
    private Thread thread;
    private static final int inaltime = 25;
    private static final int latime = 10;
    private static final int dimensiune = 27;
    private static Forma formaPrincipala;
    private static Forma formaPrincipalaUrmatoare;
    private double frame = 0.0;
    private int timpEliminare;
    private boolean rezultat;
    private boolean eliminare = false;
    private static int[] linii = new int[4];
    private static int liniiEliminate;
    private static int statisticaForme[] = new int[8];
    private static boolean gameOver = false;
    private static int directie;
    private static boolean resetState = false;
    private ZonaJoc joc = new ZonaJoc();

    private long framerate = 1000 / 60;
    private long frameStart;
    private long frameCount = 0;
    private long elapsedTime;
    private long totalElapsedTime = 0;
    private long reportedFramerate = 0;
    private double gravitate = 0.02;


    public Joc() {
        this.addKeyListener(new Input());
        new Window(this);
    }
    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
    }
    private static int calculScor() {
        return ((linii[0] * 40) * nivel() + (linii[1] * 100) * nivel() + (linii[2] * 300) * nivel() + (linii[3] * 1200) * nivel());
    }
    private static int nivel() {
        return (liniiEliminate / 5) + 1;
    }

    private static void deseneazaFormaPrincipala(Graphics g) {
        for (int i = 0; i < 4; i++) {
            g.setColor(Color.white);
            g.drawRect(264 + formaPrincipala.getPatratIndex(i).getPosX() * dimensiune, 26 + (formaPrincipala.getPatratIndex(i).getPosY() - 3) * dimensiune - dimensiune + 1, dimensiune, dimensiune);
            g.setColor(formaPrincipala.getPatratIndex(i).getCuloare());
            g.fillRect(264 + formaPrincipala.getPatratIndex(i).getPosX() * dimensiune + 1, 26 + (formaPrincipala.getPatratIndex(i).getPosY() - 3) * dimensiune - dimensiune + 2, dimensiune - 1, dimensiune - 1);//patrat
        }
    }
    private static void deseneazaCareu(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, 800, 640);
        g.setColor(Color.yellow);
        g.fillRect(260, 0, 5, 640);
        g.fillRect(534, 0, 5, 640);
        g.fillRect(260, 595, 274, 8);

        g.setColor(Color.white);
        g.setFont(new Font("arial", 1, 30));
        g.drawString("Statistica joc", 565, 60);
        g.setFont(new Font("arial", 1, 23));
        g.drawString("Forma urmatoare", 27, 450);
        g.drawString("Numar de linii " + (liniiEliminate), 27, 30);
        g.drawString("Scor " + calculScor(), 27, 100);
        g.drawString("Nivel " + nivel(), 27, 250);
        if (gameOver) {
            g.drawString("Game Over", 27, 150);
            g.setFont(new Font("arial", Font.BOLD, 10));
            g.drawString("Apasa R pentru a incepe alt joc", 27, 170);
        }
    }
    private static void deseneazaFormaUrmatoare(Graphics g) {
        for (int i = 0; i < 4; i++) {
            g.setColor(Color.white);
            if (formaPrincipalaUrmatoare instanceof FormaI) {
                g.drawRect(formaPrincipalaUrmatoare.getPatratIndex(i).getPosX() * dimensiune, 26 + (formaPrincipalaUrmatoare.getPatratIndex(i).getPosY() + 16) * dimensiune - dimensiune, dimensiune, dimensiune);//patrat
                g.setColor(formaPrincipalaUrmatoare.getPatratIndex(i).getCuloare());
                g.fillRect(formaPrincipalaUrmatoare.getPatratIndex(i).getPosX() * dimensiune + 1, 26 + (formaPrincipalaUrmatoare.getPatratIndex(i).getPosY() + 16) * dimensiune - dimensiune + 1, dimensiune - 1, dimensiune - 1);//patrat
            } else if (formaPrincipalaUrmatoare instanceof FormaJ) {
                g.drawRect(formaPrincipalaUrmatoare.getPatratIndex(i).getPosX() * dimensiune - 54, 26 + (formaPrincipalaUrmatoare.getPatratIndex(i).getPosY() + 16) * dimensiune - dimensiune, dimensiune, dimensiune);//patrat
                g.setColor(formaPrincipalaUrmatoare.getPatratIndex(i).getCuloare());
                g.fillRect(formaPrincipalaUrmatoare.getPatratIndex(i).getPosX() * dimensiune - 54 + 1, 26 + (formaPrincipalaUrmatoare.getPatratIndex(i).getPosY() + 16) * dimensiune - dimensiune + 1, dimensiune - 1, dimensiune - 1);//patrat
            } else if (formaPrincipalaUrmatoare instanceof FormaL) {
                g.drawRect(formaPrincipalaUrmatoare.getPatratIndex(i).getPosX() * dimensiune - 54, 26 + (formaPrincipalaUrmatoare.getPatratIndex(i).getPosY() + 16) * dimensiune - dimensiune, dimensiune, dimensiune);//patrat
                g.setColor(formaPrincipalaUrmatoare.getPatratIndex(i).getCuloare());
                g.fillRect(formaPrincipalaUrmatoare.getPatratIndex(i).getPosX() * dimensiune - 54 + 1, 26 + (formaPrincipalaUrmatoare.getPatratIndex(i).getPosY() + 16) * dimensiune - dimensiune + 1, dimensiune - 1, dimensiune - 1);//patrat
            } else if (formaPrincipalaUrmatoare instanceof FormaO) {
                g.drawRect(formaPrincipalaUrmatoare.getPatratIndex(i).getPosX() * dimensiune - 27, 26 + (formaPrincipalaUrmatoare.getPatratIndex(i).getPosY() + 16) * dimensiune - dimensiune, dimensiune, dimensiune);//patrat
                g.setColor(formaPrincipalaUrmatoare.getPatratIndex(i).getCuloare());
                g.fillRect(formaPrincipalaUrmatoare.getPatratIndex(i).getPosX() * dimensiune - 27 + 1, 26 + (formaPrincipalaUrmatoare.getPatratIndex(i).getPosY() + 16) * dimensiune - dimensiune + 1, dimensiune - 1, dimensiune - 1);//patrat
            } else if (formaPrincipalaUrmatoare instanceof FormaS) {
                g.drawRect(formaPrincipalaUrmatoare.getPatratIndex(i).getPosX() * dimensiune - 27, 26 + (formaPrincipalaUrmatoare.getPatratIndex(i).getPosY() + 16) * dimensiune - dimensiune, dimensiune, dimensiune);//patrat
                g.setColor(formaPrincipalaUrmatoare.getPatratIndex(i).getCuloare());
                g.fillRect(formaPrincipalaUrmatoare.getPatratIndex(i).getPosX() * dimensiune - 27 + 1, 26 + (formaPrincipalaUrmatoare.getPatratIndex(i).getPosY() + 16) * dimensiune - dimensiune + 1, dimensiune - 1, dimensiune - 1);//patrat
            } else if (formaPrincipalaUrmatoare instanceof FormaT) {
                g.drawRect(formaPrincipalaUrmatoare.getPatratIndex(i).getPosX() * dimensiune - 54, 26 + (formaPrincipalaUrmatoare.getPatratIndex(i).getPosY() + 16) * dimensiune - dimensiune, dimensiune, dimensiune);//patrat
                g.setColor(formaPrincipalaUrmatoare.getPatratIndex(i).getCuloare());
                g.fillRect(formaPrincipalaUrmatoare.getPatratIndex(i).getPosX() * dimensiune - 54 + 1, 26 + (formaPrincipalaUrmatoare.getPatratIndex(i).getPosY() + 16) * dimensiune - dimensiune + 1, dimensiune - 1, dimensiune - 1);//patrat
            } else if (formaPrincipalaUrmatoare instanceof FormaZ) {
                g.drawRect(formaPrincipalaUrmatoare.getPatratIndex(i).getPosX() * dimensiune - 27, 26 + (formaPrincipalaUrmatoare.getPatratIndex(i).getPosY() + 16) * dimensiune - dimensiune, dimensiune, dimensiune);//patrat
                g.setColor(formaPrincipalaUrmatoare.getPatratIndex(i).getCuloare());
                g.fillRect(formaPrincipalaUrmatoare.getPatratIndex(i).getPosX() * dimensiune - 27 + 1, 26 + (formaPrincipalaUrmatoare.getPatratIndex(i).getPosY() + 16) * dimensiune - dimensiune + 1, dimensiune - 1, dimensiune - 1);//patrat
            }
        }
    }
    private static void deseneazaPieseFixate(Graphics g, ZonaJoc zonajoc) {
        for (int i = 3; i < inaltime; i++) {
            for (int j = 0; j < latime; j++) {
                if (zonajoc.getOcupat(i, j) != null) {
                    g.setColor(Color.white);
                    g.fillRect(264 + j * dimensiune, 26 + (i - 3) * dimensiune - dimensiune + 1, dimensiune + 1, dimensiune + 1);
                    g.setColor(zonajoc.getOcupat(i, j).getCuloare());
                    g.fillRect(264 + j * dimensiune + 1, 26 + (i - 3) * dimensiune - dimensiune + 2, dimensiune - 1, dimensiune - 1);//patrat
                }
            }
        }
    }
    private void desenStatistaicaJoc(Graphics g) {
        FormaI.deseneazaFormaStatistica(g);
        FormaJ.deseneazaFormaStatistica(g);
        FormaL.deseneazaFormaStatistica(g);
        FormaO.deseneazaFormaStatistica(g);
        FormaS.deseneazaFormaStatistica(g);
        FormaZ.deseneazaFormaStatistica(g);
        FormaT.deseneazaFormaStatistica(g);

        g.drawString("->" + statisticaForme[0], 679, 118);
        g.drawString("->" + statisticaForme[1], 679, 167);
        g.drawString("->" + statisticaForme[2], 679, 245);
        g.drawString("->" + statisticaForme[3], 679, 309);
        g.drawString("->" + statisticaForme[4], 679, 373);
        g.drawString("->" + statisticaForme[5], 679, 478);
        g.drawString("->" + statisticaForme[6], 679, 531);
    }
    private void deseneazaZonaJoc(ZonaJoc zonajoc) {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(2);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        deseneazaCareu(g);
        deseneazaFormaPrincipala(g);
        deseneazaFormaUrmatoare(g);
        deseneazaPieseFixate(g, zonajoc);

        g.setFont(new Font("arial", Font.BOLD, 15));


        desenStatistaicaJoc(g);

        g.dispose();
        bs.show();
    }

    private void resetState(){
        joc.resetZonaJoc();
        gameOver = false;
        formaPrincipala = Forma.generareForma();
        statisticaForme[Forma.getIndexForma(formaPrincipala)]++;
        formaPrincipalaUrmatoare = Forma.generareForma();
        liniiEliminate = 0;
        for(int i = 0;i < 4;i++){
            linii[i] = 0;
        }

        for(int i = 0;i < 8;i++){
            statisticaForme[i] = 0;
        }
    }
    public boolean mergeJos() {
        if (frame >= 1.0) {
            frame = 0.0;
            return true;
        }
        frame += (gravitate * nivel());
        return false;
    }
    public boolean timerEliminare() {
        if (timpEliminare == 10) {
            timpEliminare = 0;
            return true;
        }
        timpEliminare++;
        return false;
    }
    public boolean procesareInput(ZonaJoc joc){
        if (mergeJos()) {
            return formaPrincipala.miscareFormaPrincipalaJos(joc);
        }
        for(int i = 0;i < 6;i++) {
            directie = Input.getDirectie(i);
            if((gameOver) && (directie == 5)){
                resetState();
            }
            else {
                boolean aux = false;
                if ((directie == 3) || (directie == 4)) {
                    formaPrincipala.rotireForma(joc, formaPrincipala, directie);
                    aux = false;
                } else if (directie == 1) {
                    frame += 1.0;
                } else {
                    aux = formaPrincipala.miscareFormaPrincipala(directie, joc);
                }
                if (aux) {
                    Input.resetInput();
                    return true;
                }
            }
        }
        return false;
    }
    private void logicaJoc(ZonaJoc joc){
        if (!eliminare) {
            System.out.println(System.currentTimeMillis() / 1000.0 + " " +directie);
            rezultat = procesareInput(joc);
            if (rezultat) {
                joc.adaugarePieseZona(formaPrincipala);
                eliminare = joc.pregatireEliminareLinii();
                formaPrincipala = formaPrincipalaUrmatoare;
                if (formaPrincipala.gameOver(joc)) {
                    gameOver = true;
                } else {
                    statisticaForme[Forma.getIndexForma(formaPrincipala)]++;
                    formaPrincipalaUrmatoare = Forma.generareForma();
                    rezultat = false;
                }
            }
        } else if (timerEliminare()) {
            int aux = joc.eliminareLinii();
            linii[aux - 1]++;
            liniiEliminate += aux;
            eliminare = false;
        }
    }


    @Override
    public void run() {
        formaPrincipala = Forma.generareForma();
        statisticaForme[Forma.getIndexForma(formaPrincipala)]++;
        formaPrincipalaUrmatoare = Forma.generareForma();
        this.requestFocus();
        while (true) {
            frameStart = System.currentTimeMillis();
            if (!gameOver) {
                logicaJoc(joc);
            } else {
                rezultat = procesareInput(joc);
                rezultat = false;
            }
            deseneazaZonaJoc(joc);
            elapsedTime = System.currentTimeMillis() - frameStart;
            try {
                if (elapsedTime < framerate) {
                    Thread.sleep(framerate - elapsedTime);
                } else {
                    Thread.sleep(5);
                }
            } catch (InterruptedException e) {
                break;
            }
            frameCount++;
            totalElapsedTime += (System.currentTimeMillis() - frameStart);
            if (totalElapsedTime > 1000) {
                reportedFramerate = (long) (((double) frameCount / (double) totalElapsedTime) * 1000.0);
                frameCount = 0;
                totalElapsedTime = 0;
            }
        }
    }
}


