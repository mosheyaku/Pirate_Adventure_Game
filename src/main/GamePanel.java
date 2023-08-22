package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.PlayerConstants.*;
import static utils.Constants.Directions.*;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private Game game;


    public GamePanel(Game game) {
        mouseInputs = new MouseInputs(this);
        this.game=game;
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public Game getGame(){
        return game;
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

//    public void changeXDelta(int value) {
//        this.xDelta += value;
//    }
//
//    public void changeYDelta(int value) {
//        this.yDelta += value;
//    }


    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        game.render(graphics);
    }

    public void updateGame() {

    }
}
