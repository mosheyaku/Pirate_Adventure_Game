package ui;

import main.Game;
import utils.Constants;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.PauseButtons.SOUND_SIZE;
import static utils.Constants.UI.URMButtons.URM_SIZE;

public class PauseOverlay {
    private BufferedImage backgroundImg;
    private int backgroundX, backgroundY, backgroundWidth, backgroundHeight;
    private SoundButton musicButton, soundEffectsButton;
    private UrmButton menuB, replyB, unpauseB;

    public PauseOverlay() {
        loadBackground();
        createSoundButtons();
        createUrmButtons();
    }

    private void createUrmButtons() {
        int menuX = (int) (313 * Game.SCALE);
        int replyX = (int) (387 * Game.SCALE);
        int unpauseX = (int) (462 * Game.SCALE);
        menuB = new UrmButton(menuX, backgroundY, URM_SIZE, URM_SIZE, 2);
        replyB = new UrmButton(replyX, backgroundY, URM_SIZE, URM_SIZE, 1);
        unpauseB = new UrmButton(unpauseX, backgroundY, URM_SIZE, URM_SIZE, 0);

    }

    private void createSoundButtons() {
        int soundX = (int) (450 * Game.SCALE);
        int musicY = (int) (140 * Game.SCALE);
        int soundEffectsY = (int) (186 * Game.SCALE);
        musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        soundEffectsButton = new SoundButton(soundX, soundEffectsY, SOUND_SIZE, SOUND_SIZE);

    }

    private void loadBackground() {
        backgroundImg = LoadSave.getPositionsAtlas(LoadSave.PAUSE_BACKGROUND);
        backgroundWidth = (int) (backgroundImg.getWidth() * Game.SCALE);
        backgroundHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
        backgroundX = Game.GAME_WIDTH / 2 - backgroundWidth / 2;
        backgroundY = (int) (25 * Game.SCALE);

    }

    public void update() {
        musicButton.update();
        soundEffectsButton.update();
        menuB.update();
        replyB.update();
        unpauseB.update();
    }

    public void draw(Graphics graphics) {
        graphics.drawImage(backgroundImg, backgroundX, backgroundY, backgroundWidth, backgroundHeight, null);
        musicButton.draw(graphics);
        soundEffectsButton.draw(graphics);

        menuB.draw(graphics);
        replyB.draw(graphics);
        unpauseB.draw(graphics);
    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, musicButton))
            musicButton.setMousePressed(true);
        else if (isIn(e, soundEffectsButton))
            soundEffectsButton.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, musicButton)) {
            if (musicButton.isMousePressed())
                musicButton.setMuted(!musicButton.isMuted());
        } else if (isIn(e, soundEffectsButton)) {
            if (soundEffectsButton.isMousePressed())
                soundEffectsButton.setMuted(!soundEffectsButton.isMuted());
        }
        musicButton.resetBools();
        soundEffectsButton.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        soundEffectsButton.setMouseOver(false);

        if (isIn(e, musicButton))
            musicButton.setMouseOver(true);
        else if (isIn(e, soundEffectsButton))
            soundEffectsButton.setMouseOver(true);


    }

    private boolean isIn(MouseEvent e, PauseButton button) {
        return button.getBounds().contains(e.getX(), e.getY());
    }
}
