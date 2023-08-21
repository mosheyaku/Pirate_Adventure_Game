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
    private float xDelta = 100, yDelta = 100;
    private BufferedImage img;
    private BufferedImage[][] pirateAnimation;
    private int animationMovement, animationIndex, animationSpeed = 15;
    private int playerAction = STAYING;
    private int playerDirection = -1;
    private boolean moving = false;


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
        pirateAnimation = new BufferedImage[9][6];
        for (int i = 0; i < pirateAnimation.length; i++)
            for (int j = 0; j < pirateAnimation[i].length; j++) {
                pirateAnimation[i][j] = img.getSubimage(j * 64, i * 40, 64, 40);
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

    public void setDirection(int direction) {
        this.playerDirection = direction;
        moving= true;
    }

    public void setMoving(boolean moving){
        this.moving=moving;
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(pirateAnimation[playerAction][animationIndex], (int) xDelta, (int) yDelta, 128, 80, null);

    }

    private void updatePosition() {
        if(moving){
            switch (playerDirection){
                case LEFT:
                    xDelta-=2;
                    break;
                case UP:
                    yDelta-=2;
                    break;
                case RIGHT:
                    xDelta+=2;
                    break;
                case DOWN:
                    yDelta+=2;
                    break;
            }
        }
    }

    private void setAnimation() {
        if(moving)
            playerAction=RUNNING;
        else
            playerAction=STAYING;
    }

    private void updateAnimationMovement() {
        animationMovement++;
        if (animationMovement >= animationSpeed) {
            animationMovement = 0;
            animationIndex++;
            animationIndex %= getSpriteAmount(playerAction);
        }
    }

    public void updateGame(){
        updateAnimationMovement();
        setAnimation();
        updatePosition();
    }
}
