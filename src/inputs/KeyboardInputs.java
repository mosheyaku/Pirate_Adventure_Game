package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W:
                System.out.println("it's W");
                break;
            case KeyEvent.VK_A:
                System.out.println("it's A");
                break;
            case KeyEvent.VK_S:
                System.out.println("it's S");
                break;
            case KeyEvent.VK_D:
                System.out.println("it's D");
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
