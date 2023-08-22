package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.Directions.*;
import static utils.Constants.Directions.DOWN;
import static utils.Constants.PlayerConstants.*;

public class Player extends Entity{

    private BufferedImage[][] pirateAnimation;

    private int animationMovement, animationIndex, animationSpeed = 15;
    private int playerAction = STAYING;
    private int playerDirection = -1;
    private boolean moving = false;
    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
    }

    public void update(){
        updateAnimationMovement();
        setAnimation();
        updatePosition();
    }

    public void render(Graphics graphics){
        graphics.drawImage(pirateAnimation[playerAction][animationIndex], (int) x, (int) y, 128, 80, null);
    }
    private void loadAnimations() {
        InputStream is = getClass().getResourceAsStream("/images/pirate_positions.png");
        try {
            BufferedImage img = ImageIO.read(is);

            pirateAnimation = new BufferedImage[9][6];
            for (int i = 0; i < pirateAnimation.length; i++)
                for (int j = 0; j < pirateAnimation[i].length; j++) {
                    pirateAnimation[i][j] = img.getSubimage(j * 64, i * 40, 64, 40);
                }

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


    public void setDirection(int direction) {
        this.playerDirection = direction;
        moving= true;
    }

    public void setMoving(boolean moving){
        this.moving=moving;
    }

    private void updatePosition() {
        if(moving){
            switch (playerDirection){
                case LEFT:
                    x-=2;
                    break;
                case UP:
                    y-=2;
                    break;
                case RIGHT:
                    x+=2;
                    break;
                case DOWN:
                    y+=2;
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
}
