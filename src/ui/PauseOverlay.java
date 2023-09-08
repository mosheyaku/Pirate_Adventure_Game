package ui;

import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class PauseOverlay {
    private BufferedImage backgroundImg;
    private int backgroundX, backgroundY, backgroundWidth, backgroundHeight;

    public PauseOverlay() {
        loadBackground();
    }

    private void loadBackground() {
        backgroundImg = LoadSave.getPositionsAtlas(LoadSave.PAUSE_BACKGROUND);
        backgroundWidth = (int) (backgroundImg.getWidth() * Game.SCALE);
        backgroundHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
        backgroundX = Game.GAME_WIDTH / 2 - backgroundWidth / 2;
        backgroundY = (int) (25 * Game.SCALE);

    }

    public void update() {
    }

    public void draw(Graphics graphics) {
        graphics.drawImage(backgroundImg, backgroundX, backgroundY, backgroundWidth, backgroundHeight, null);
    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

    }
}
