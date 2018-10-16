import java.awt.*;

public class FormaJ extends Forma {


    private FormaJ(Patrat[] formaJ) {
        super(formaJ);
    }


    public static Forma generareFormaJ(Color culoare) {
        Patrat[] formaJ = {new Patrat(culoare, 5, 2),
                new Patrat(culoare, 6, 2),
                new Patrat(culoare, 7, 2),
                new Patrat(culoare, 7, 3)};
        return new FormaJ(formaJ);
    }

    public static int getPivot() {
        return 1;
    }

    public static void deseneazaFormaStatistica(Graphics g) {
        g.setColor(Color.white);

        g.drawRect(600, 150, 26, 26);
        g.drawRect(627, 150, 26, 26);
        g.drawRect(654, 150, 26, 26);
        g.drawRect(654, 177, 26, 26);
        g.setColor(Color.red);
        g.fillRect(601, 151, 25, 25);
        g.fillRect(628, 151, 25, 25);
        g.fillRect(655, 151, 25, 25);
        g.fillRect(655, 178, 25, 25);
    }

}

