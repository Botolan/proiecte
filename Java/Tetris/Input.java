import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Input extends KeyAdapter {
    private static boolean []instructiuni = new boolean[6];

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_LEFT){
            instructiuni[0] = true;
        }
        else if (keyCode == KeyEvent.VK_DOWN){
            instructiuni[1] = true;
        }
        else if (keyCode == KeyEvent.VK_RIGHT){
            instructiuni[2] = true;
        }
        else if (keyCode == KeyEvent.VK_Z){
            instructiuni[3] = true;
        }
        else if(keyCode == KeyEvent.VK_X){
            instructiuni[4] = true;
        }
        else if(keyCode == KeyEvent.VK_R){
            instructiuni[5] = true;
        }
     }



    public static int getDirectie(int index){
        if(instructiuni[index]){
            instructiuni[index] = false;
            return index;
        }
        return -1;
    }

    public static void resetInput(){
        instructiuni[0] = false;
        instructiuni[1] = false;
        instructiuni[2] = false;
        instructiuni[3] = false;
        instructiuni[4] = false;
        instructiuni[5] = false;
    }

}
