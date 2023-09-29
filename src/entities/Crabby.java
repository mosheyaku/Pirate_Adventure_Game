package entities;

import main.Game;

import static utils.Constants.EnemyConstants.*;

public class Crabby extends Enemy {
    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        initHitbox(x, y, (int) (22 * Game.SCALE), (int) (26 * Game.SCALE));
    }

    private void updateMove(int[][] levelData) {
        if (firstUpdate)
            firstUpdateCheck(levelData);
        if (inAir) {
            updateInAir(levelData);
        } else {
            switch (enemyState) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    move(levelData);
                    break;
            }
        }
    }
}