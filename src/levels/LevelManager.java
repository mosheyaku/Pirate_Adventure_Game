package levels;

import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelManager {
    private Game game;
    private BufferedImage levelElement;
    public LevelManager(Game game){
        this.game=game;
        levelElement= LoadSave.getPositionsAtlas(LoadSave.LEVEL_ATLAS);
    }

    public void draw(Graphics graphics){
        graphics.drawImage(levelElement, 0 , 0 ,null);
    }

    public void update(){

    }

}
