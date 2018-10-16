import javax.swing.*;
import java.awt.*;

public class Window extends Canvas{

    public Window(Joc joc){
        JFrame frame = new JFrame("Tetris");
        frame.setMaximumSize(new Dimension(800,640));
        frame.setPreferredSize(new Dimension(800,640));
        frame.setMinimumSize(new Dimension(800,640));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(joc);
        frame.setVisible(true);
        joc.start();
    }

    public static void main(String[] args){
        new Joc();
    }
}