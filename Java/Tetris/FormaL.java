import java.awt.*;

public class FormaL extends Forma {

    private FormaL(Patrat []formaL){
        super(formaL);
    }




    public static Forma generareFormaL(Color culoare){
        Patrat []formaL = {new Patrat(culoare,5,3),
                           new Patrat(culoare,5,2),
                           new Patrat(culoare,6,2),
                           new Patrat(culoare,7,2)};
        return new FormaL(formaL);
    }

    public static int getPivot(){
        return 2;
    }

    public static void deseneazaFormaStatistica(Graphics g) {
        g.setColor(Color.white);

        g.drawRect(600, 232, 26, 26);
        g.drawRect(627, 232, 26, 26);
        g.drawRect(654, 232, 26, 26);
        g.drawRect(654, 206, 26, 26);
        g.setColor(Color.red);
        g.fillRect(601, 233, 25, 25);
        g.fillRect(628, 233, 25, 25);
        g.fillRect(655, 233, 25, 25);
        g.fillRect(655, 207, 25, 25);
    }

}
