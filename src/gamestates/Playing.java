package gamestates;

import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.PauseOverlay;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.Constants.Environment.*;

public class Playing extends State implements StateMethods {
    private Player player;
    private LevelManager levelManager;
    private PauseOverlay pauseOverlay;
    private boolean paused = false;
    private int xLevelOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int levelTilesWide = LoadSave.getLevelData()[0].length;
    private int maxTilesOffset = levelTilesWide - Game.TILES_IN_WIDTH;
    private int maxLevelOffsetX = maxTilesOffset * Game.TILES_SIZE;
    private BufferedImage backgroundIMG, bigCloud;


    public Playing(Game game) {
        super(game);
        initClasses();
        backgroundIMG = LoadSave.getPositionsAtlas(LoadSave.PLAYING_BACKGROUND_IMG);
        bigCloud = LoadSave.getPositionsAtlas(LoadSave.BIG_CLOUDS);
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE));
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        pauseOverlay = new PauseOverlay(this);
    }

    @Override
    public void update() {
        if (!paused) {
            levelManager.update();
            player.update();
            checkCloseToBorder();
        } else
            pauseOverlay.update();
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLevelOffset;
        if (diff > rightBorder)
            xLevelOffset += diff - rightBorder;
        else if (diff < leftBorder)
            xLevelOffset += diff - leftBorder;

        if (xLevelOffset > maxLevelOffsetX)
            xLevelOffset = maxLevelOffsetX;
        else if (xLevelOffset < 0)
            xLevelOffset = 0;
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.drawImage(backgroundIMG, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        drawClouds(graphics);
        levelManager.draw(graphics, xLevelOffset);
        player.render(graphics, xLevelOffset);
        if (paused) {
            graphics.setColor(new Color(0, 0, 0, 150));
            graphics.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(graphics);
        }
    }

    private void drawClouds(Graphics graphics) {
        for (int i = 0; i < 3; i++)
            graphics.drawImage(bigCloud, i * BIG_CLOUD_WIDTH, (int) (204 * Game.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
            player.setAttacking(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paused)
            pauseOverlay.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseMoved(e);
    }

    public void mouseDragged(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseDragged(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
            case KeyEvent.VK_ESCAPE:
                paused = !paused;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void windowFocusLost() {
        player.resetDirectionBoolean();
    }

    public void unpauseGame() {
        paused = false;
    }
}
