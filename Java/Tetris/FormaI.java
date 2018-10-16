import java.awt.*;

public class FormaI extends Forma {

    private FormaI(Patrat []formaI){
        super(formaI);
    }


    public static Forma generareFormaI(Color culoare){
        Patrat []formaI = {new Patrat(culoare,3,2),
                           new Patrat(culoare,4,2),
                           new Patrat(culoare,5,2),
                           new Patrat(culoare,6,2)};
        return new FormaI(formaI);
    }

    public static int getPivot(){
        return 2;
    }


    public static void deseneazaFormaStatistica(Graphics g) {
        g.setColor(Color.white);
        g.drawRect(574,100,26,26);
        g.drawRect(600,100,26,26);
        g.drawRect(627,100,26,26);
        g.drawRect(654,100,26,26);
        g.setColor(Color.red);
        g.fillRect(575,101,25,25);
        g.fillRect(601,101,25,25);
        g.fillRect(628,101,25,25);
        g.fillRect(655,101,25,25);
    }

}
