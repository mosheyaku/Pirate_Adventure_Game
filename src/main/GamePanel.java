package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private BufferedImage img;
    private BufferedImage[] pirateAnimation;
    private int animationMovement, animationIndex, animationSpeed= 15;


    public GamePanel() {
        mouseInputs = new MouseInputs(this);
        importImg();
        loadAnimations();
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void loadAnimations() {
        pirateAnimation= new BufferedImage[5];
        for (int i = 0; i < pirateAnimation.length; i++) {
            pirateAnimation[i]= img.getSubimage(i*64, 0, 64, 40);
        }

    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/images/pirate_positions.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    public void changeXDelta(int value) {
        this.xDelta += value;
    }

    public void changeYDelta(int value) {
        this.yDelta += value;
    }

    public void setRectPosition(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        updateAnimationMovement();
        graphics.drawImage(pirateAnimation[animationIndex], (int) xDelta, (int) yDelta, 128, 80, null);

    }

    private void updateAnimationMovement() {
        animationMovement++;
        if(animationMovement>=animationSpeed){
            animationMovement=0;
            animationIndex++;
            animationIndex %= pirateAnimation.length;

        }
    }
}
