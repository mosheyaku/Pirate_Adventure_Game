package entities;

import static utils.Constants.EnemyConstants.*;


public abstract class Enemy extends Entity {
    private int animationIndex, enemyState, enemyType;
    private int animationMovement, animationSpeed = 25;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
    }

    private void updateAnimationMovement() {
        animationMovement++;
        if (animationMovement >= animationSpeed) {
            animationMovement = 0;
            animationIndex++;
            if (animationIndex >= getPositionsAmount(enemyType,enemyState))
                animationIndex = 0;
        }
    }

    public void update() {
        updateAnimationMovement();
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }
}
