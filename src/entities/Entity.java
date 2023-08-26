package entities;

import java.awt.*;

public abstract class Entity {
    protected float x, y;
    protected int width, height;

    protected Rectangle hitbox;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        initHitbox();
    }

    private void initHitbox() {
        hitbox = new Rectangle((int) x, (int) y, width, height);
    }

    protected void updateHitbox() {
        hitbox.x = (int) x;
        hitbox.y = (int) y;
    }

    protected void drawHitbox(Graphics graphics){
        graphics.setColor(Color.PINK);
        graphics.drawRect(hitbox.x, hitbox.y, hitbox.width,hitbox.height);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
