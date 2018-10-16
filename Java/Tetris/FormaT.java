import java.awt.*;

public class FormaT extends Forma {

    private FormaT(Patrat []formaT){
        super(formaT);
    }



    public static Forma generareFormaT(Color culoare){
        Patrat []formaT = {new Patrat(culoare,5,2),
                           new Patrat(culoare,6,2),
                           new Patrat(culoare,7,2),
                           new Patrat(culoare,6,3)};
        return new FormaT(formaT);
    }

    public static int getPivot(){
        return 1;
    }

    public static void deseneazaFormaStatistica(Graphics g) {
        g.setColor(Color.white);
        g.drawRect(600,513,26,26);
        g.drawRect(627,513,26,26);
        g.drawRect(654,513,26,26);
        g.drawRect(627,540,26,26);
        g.setColor(Color.red);
        g.fillRect(601,514,25,25);
        g.fillRect(628,514,25,25);
        g.fillRect(655,514,25,25);
        g.fillRect(628,541,25,25);


    }

}
