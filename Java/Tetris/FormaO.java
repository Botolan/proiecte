import java.awt.*;

public class FormaO extends Forma {

    private FormaO(Patrat []formaO){
        super(formaO);
    }


    public static Forma generareFormaO(Color culoare){
        Patrat []formaO = {new Patrat(culoare,5,2),
                           new Patrat(culoare,6,2),
                           new Patrat(culoare,5,3),
                           new Patrat(culoare,6,3)};
        return new FormaO(formaO);
    }

    public static int getPivot(){
        return 2;
    }


    public static void deseneazaFormaStatistica(Graphics g) {
        g.setColor(Color.white);

        g.drawRect(627,278,26,26);
        g.drawRect(654,278,26,26);
        g.drawRect(627,305,26,26);
        g.drawRect(654,305,26,26);
        g.setColor(Color.red);
        g.fillRect(628,279,25,25);
        g.fillRect(655,279,25,25);
        g.fillRect(628,306,25,25);
        g.fillRect(655,306,25,25);
    }

}
