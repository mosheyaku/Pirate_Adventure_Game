package entities;

import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.PlayerConstants.*;

import static utils.HelpMethods.canMoveHere;

public class Player extends Entity {

    private BufferedImage[][] pirateAnimation;

    private int animationMovement, animationIndex, animationSpeed = 25;
    private int playerAction = STAYING;
    private boolean moving = false, attacking = false;
    private int[][] levelData;

    private float playerSpeed = 2.0f;

    private boolean left, right, up, down;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void resetDirectionBoolean() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void update() {
        updatePosition();
        updateHitbox();
        updateAnimationMovement();
        setAnimation();
    }

    public void render(Graphics graphics) {
        graphics.drawImage(pirateAnimation[playerAction][animationIndex], (int) x, (int) y, width, height, null);
        drawHitbox(graphics);
    }

    private void loadAnimations() {
        BufferedImage img = LoadSave.getPositionsAtlas(LoadSave.PLAYER_ATLAS);

        pirateAnimation = new BufferedImage[9][6];
        for (int i = 0; i < pirateAnimation.length; i++)
            for (int j = 0; j < pirateAnimation[i].length; j++) {
                pirateAnimation[i][j] = img.getSubimage(j * 64, i * 40, 64, 40);
            }
    }

    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;
    }

    private void updatePosition() {
        moving = false;
        if (!left && !right && !up && !down)
            return;

        float xSpeed = 0, ySpeed = 0;

        if (left && !right)
            xSpeed = -playerSpeed;
        else if (right && !left)
            xSpeed = playerSpeed;


        if (up && !down)
            ySpeed = -playerSpeed;
        else if (down && !up)
            ySpeed = playerSpeed;

        if (canMoveHere(x + xSpeed, y + ySpeed, width, height, levelData)) {
            this.x += xSpeed;
            this.y += ySpeed;
            moving = true;
        }

    }

    private void setAnimation() {
        int startAnimation = playerAction;
        if (moving)
            playerAction = RUNNING;
        else
            playerAction = STAYING;
        if (attacking)
            playerAction = ATTACK_1;
        if (startAnimation != playerAction)
            resetAnimationMovement();
    }

    private void resetAnimationMovement() {
        animationMovement = 0;
        animationIndex = 0;
    }

    private void updateAnimationMovement() {
        animationMovement++;
        if (animationMovement >= animationSpeed) {
            animationMovement = 0;
            animationIndex++;
            if (animationIndex >= getSpriteAmount(playerAction)) {
                animationIndex = 0;
                attacking = false;
            }
        }
    }
}
