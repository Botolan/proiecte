import java.awt.*;

public class Patrat {
    private Color culoarePatrat;
    private int posX;
    private int posY;

    public Patrat(Color culoarePatrat, int posX, int posY){
        this.culoarePatrat = culoarePatrat;
        this.posX = posX;
        this.posY = posY;
    }

    public Color getCuloare(){
        return this.culoarePatrat;
    }
    public int getPosX() {
        return posX;
    }
    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPatratEliminare(){
        this.culoarePatrat = Color.white;
    }

}
