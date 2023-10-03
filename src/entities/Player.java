package entities;

import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.PlayerConstants.*;

import static utils.HelpMethods.*;
import static utils.HelpMethods.getEntityXPosNextToWall;

public class Player extends Entity {

    private BufferedImage[][] pirateAnimation;

    private int animationMovement, animationIndex, animationSpeed = 25;
    private int playerAction = STAYING;
    private boolean moving = false, attacking = false;
    private int[][] levelData;
    private float playerSpeed = 1.0f * Game.SCALE;
    private boolean left, right, up, down, jump;
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    private BufferedImage statusBarImg;

    private int statusBarWidth = (int) (192 * Game.SCALE);
    private int statusBarHeight = (int) (58 * Game.SCALE);
    private int statusBarX = (int) (10 * Game.SCALE);
    private int statusBarY = (int) (10 * Game.SCALE);


    private int healthBarWidth = (int) (150 * Game.SCALE);
    private int healthBarHeight = (int) (4 * Game.SCALE);
    private int healthBarXStart = (int) (34 * Game.SCALE);
    private int healthBarYStart = (int) (14 * Game.SCALE);

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, (int) (20 * Game.SCALE), (int) (27 * Game.SCALE));
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

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void resetDirectionBoolean() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void update() {
        updatePosition();
        updateAnimationMovement();
        setAnimation();
    }

    public void render(Graphics graphics, int levelOffset) {
        graphics.drawImage(pirateAnimation[playerAction][animationIndex], (int) (hitbox.x - xDrawOffset) - levelOffset, (int) (hitbox.y - yDrawOffset), width, height, null);
//        drawHitbox(graphics, levelOffset);

        drawUI(graphics);
    }

    private void drawUI(Graphics graphics) {
        graphics.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
    }

    private void loadAnimations() {
        BufferedImage img = LoadSave.getPositionsAtlas(LoadSave.PLAYER_ATLAS);

        pirateAnimation = new BufferedImage[9][6];
        for (int i = 0; i < pirateAnimation.length; i++)
            for (int j = 0; j < pirateAnimation[i].length; j++) {
                pirateAnimation[i][j] = img.getSubimage(j * 64, i * 40, 64, 40);
            }
        statusBarImg = LoadSave.getPositionsAtlas(LoadSave.STATUS_BAR);
    }

    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;
        if (!isEntityOnFloor(hitbox, levelData))
            inAir = true;
    }

    private void updatePosition() {
        moving = false;
        if (jump)
            jump();

        if (!inAir)
            if (!right && !left || (right && left))
                return;

        float xSpeed = 0;

        if (left)
            xSpeed -= playerSpeed;
        if (right)
            xSpeed += playerSpeed;

        if (!inAir) {
            if (!isEntityOnFloor(hitbox, levelData))
                inAir = true;
        }

        if (inAir) {
            if (canMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitbox.y = getEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }
        } else
            updateXPos(xSpeed);
        moving = true;
    }

    private void jump() {
        if (inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if (canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = getEntityXPosNextToWall(hitbox, xSpeed);
        }
    }

    private void setAnimation() {
        int startAnimation = playerAction;
        if (moving)
            playerAction = RUNNING;
        else
            playerAction = STAYING;
        if (inAir) {
            if (airSpeed < 0)
                playerAction = JUMP;
            else
                playerAction = FALLING;
        }
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
