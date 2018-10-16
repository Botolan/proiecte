import java.awt.*;

public class FormaZ extends Forma {

    private FormaZ(Patrat []formaZ){
        super(formaZ);
    }



    public static Forma generareFormaZ(Color culoare){
        Patrat []formaZ = {new Patrat(culoare,4,3),
                           new Patrat(culoare,5,3),
                           new Patrat(culoare,5,2),
                           new Patrat(culoare,6,2)};
        return new FormaZ(formaZ);
    }

    public static int getPivot(){
        return 1;
    }

    public static void deseneazaFormaStatistica(Graphics g) {
        g.setColor(Color.white);
        g.drawRect(600,432,26,26);
        g.drawRect(627,432,26,26);
        g.drawRect(627,459,26,26);
        g.drawRect(654,459,26,26);
        g.setColor(Color.red);
        g.fillRect(601,433,25,25);
        g.fillRect(628,433,25,25);
        g.fillRect(628,460,25,25);
        g.fillRect(655,460,25,25);

    }

}
