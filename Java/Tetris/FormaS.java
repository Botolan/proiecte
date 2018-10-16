import java.awt.*;

public class FormaS extends Forma {

    private FormaS(Patrat []formaO){
        super(formaO);
    }



    public static Forma generareFormaS(Color culoare){
        Patrat []formaS = {new Patrat(culoare,4,2),
                           new Patrat(culoare,5,2),
                           new Patrat(culoare,5,3),
                           new Patrat(culoare,6,3)};
        return new FormaS(formaS);
    }

    public static int getPivot(){
        return 1;
    }

    public static void deseneazaFormaStatistica(Graphics g) {
        g.setColor(Color.white);
        g.drawRect(600,382,26,26);
        g.drawRect(627,382,26,26);
        g.drawRect(627,355,26,26);
        g.drawRect(654,355,26,26);
        g.setColor(Color.red);
        g.fillRect(601,383,25,25);
        g.fillRect(628,383,25,25);
        g.fillRect(628,356,25,25);
        g.fillRect(655,356,25,25);
    }

}
