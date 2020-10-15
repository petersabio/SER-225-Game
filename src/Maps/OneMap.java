package Maps;

import Enemies.BugEnemy;
import Enemies.DinosaurEnemy;
import Engine.ImageLoader;
import EnhancedMapTiles.EndLevelBox;
import EnhancedMapTiles.HorizontalMovingPlatform;
import GameObject.GameObject;
import GameObject.Rectangle;
import Level.*;
import NPCs.Walrus;
import Tilesets.CommonTileset;
import Utils.Direction;
import Utils.Point;
import SpriteFont.SpriteFont;
import NPCs.TutorialWalrus;


import java.awt.*;
import java.util.ArrayList;

public class OneMap extends Map {
    public OneMap() {
        super("one_map.txt", new CommonTileset(), new Point(1,9));
    }//end constructor

    //ADDING ENEMIES
    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new BugEnemy(getPositionByTileIndex(20, 5), Direction.LEFT));
        enemies.add(new BugEnemy(getPositionByTileIndex(16, 6), Direction.LEFT));
        return enemies;
    }

    //ADDING SPECIAL MAP TILES
    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(9, 7),
                getPositionByTileIndex(12, 7),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));

        enhancedMapTiles.add(new EndLevelBox(
                getPositionByTileIndex(28, 7)
        ));
        return enhancedMapTiles;
    }



}// end class