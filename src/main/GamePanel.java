package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;

    private int xDelta=100, yDelta=100;
    public GamePanel(){
        mouseInputs= new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }
    public void changeXDelta(int value){
        this.xDelta+=value;
        repaint();
    }

    public void changeYDelta(int value){
        this.yDelta+=value;
        repaint();
    }

    public void setRectPosition(int x, int y){
        this.xDelta=x;
        this.yDelta=y;
        repaint();
    }
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        graphics.fillRect(xDelta,yDelta,200,50);
    }
}
